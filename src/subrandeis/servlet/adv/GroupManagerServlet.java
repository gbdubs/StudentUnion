package subrandeis.servlet.adv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Group;
import subrandeis.entities.Person;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class GroupManagerServlet extends HttpServlet{

	static Objectify ofy = ObjectifyAPI.ofy();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			Group g = Group.get(req.getParameter("groupId"));
			boolean hasPermission = p.owner || UserAPI.isGoogleAdmin();
			if (g != null){
				hasPermission = hasPermission || g.leaders.contains(p.email);
				if (hasPermission){
					// Sets the atributes that are rendered.
					req.setAttribute("group", g);
					req.setAttribute("currentUser", p);
					req.setAttribute("logoutUrl", UserAPI.logoutUrl());
					
					// Finishes up, sends to the console page.
					resp.setContentType("text/html");
					RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/group-manager.jsp");
					jsp.forward(req, resp);	
					return;
				}
			} else {
				hasPermission = hasPermission || p.admin;
				if (hasPermission){
					req.setAttribute("groups", Group.getAllGroups());
					req.setAttribute("currentUser", p);
					req.setAttribute("logoutUrl", UserAPI.logoutUrl());
					
					// Finishes up, sends to the console page.
					resp.setContentType("text/html");
					RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/group-manager-list.jsp");
					jsp.forward(req, resp);	
					return;
				}
			}
			String error = "There was an error in rendering the group manager for the group requested with id [%s]. The UserAPI was [%s].";
			error = String.format(error, req.getParameter("groupId"), UserAPI.email());
			Log.warn(error);
			resp.getWriter().println(error);
			return;
		}
		resp.sendRedirect("/login-admin?goto=%2Fgroup-manager");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String manage = req.getParameter("manage");
		
		if (manage == null){
			resp.getWriter().println("Missing parameter [manage].\n");
		}
		
		if ("nameOrPageUrlOrDescription".equals(manage)){
			doNamePageUrlDescriptionManagement(req, resp);
		} else if ("roles".equals(manage)){
			doRoleManagement(req, resp);
		} else if ("members".equals(manage)){
			doMemberManagement(req, resp);
		} else if ("updatePage".equals(manage)){
			doPageUpdate(req, resp);
		} else {
			resp.getWriter().println("Incorrect value for parameter [manage] passed.");
		}		
	}
	
	private void doPageUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupId = req.getParameter("groupId");
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			Group g = Group.get(groupId);
			if (g != null && p != null && (UserAPI.isGoogleAdmin() || p.owner || g.leaders.contains(p.email) || g.members.contains(p.email))){
				g.updateMembershipPage(this);
				Log.info(String.format("User [%s] successfully triggered an update of the group [%s][%s].", p.email, g.name, g.id));	
				resp.sendRedirect("/group-manager?groupId="+groupId);
				return;
			}
			String response = String.format("Something is wrong in the name and page url management of the group. The incorrect call was made by uaser [%s]\n", UserAPI.email());
			Log.warn(response);
			resp.getWriter().println(response);
			return;
		}
		resp.sendRedirect("/login-admin?goto=%2Fgroup-manager");
	}

	public void doNamePageUrlDescriptionManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String groupId = req.getParameter("groupId");
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			Group g = Group.get(groupId);

			if (g != null && p != null && (UserAPI.isGoogleAdmin() || p.owner || g.leaders.contains(p.email))){
				String name = req.getParameter("name");
				String pageURL = req.getParameter("pageUrl");
				String description = req.getParameter("description");
				if (name != null && pageURL != null){
					g.name = name;
					g.pageUrl = pageURL;
					g.description = description;
					ofy.save().entity(g);
					Log.info(String.format("Successfully updated the name and page URL and description of group [%s] to [%s], [%s] and [%s] respectively.\n", g.id, g.name, g.pageUrl, g.description));	
					resp.sendRedirect("/group-manager?groupId="+groupId);
					return;
				} 
				resp.getWriter().println("Either the name or pageURL was not passed properly. Check your API Call.\n");
				return;
			}
			Log.warn(String.format("Something is wrong in the name and page url management of the group. The incorrect call was made by uaser [%s]\n", UserAPI.email()));
			return;
		}
		resp.sendRedirect("/login-admin?goto=%2Fgroup-manager");
	}
	
	public void doRoleManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		if (UserAPI.loggedIn()){
		
			String groupId = req.getParameter("groupId");
			Person p = Person.get(UserAPI.email());
			Group g = Group.get(groupId);

			if (g != null && p != null && (UserAPI.isGoogleAdmin() || p.owner || g.leaders.contains(p.email))){
				int roleIndex = 0;
				Map<String, String> roles = new HashMap<String, String>();
				while (req.getParameter("role" + roleIndex) != null){
					String emailX = req.getParameter("email" + roleIndex);
					String roleX = req.getParameter("role" + roleIndex);
					roles.put(emailX, roleX);
					roleIndex++;
				}
				g.roles = roles;
				ofy.save().entity(g);
				Log.info(String.format("Updated the roles for group [%s][%s].", g.id, g.name));
				resp.sendRedirect("/group-manager");
				return;
			}
			String warning = String.format("UserAPI [%s] attempted to update roles for group [%s][%s], but was turned away for insufficent permissions.\n", p.email, g.id, g.name);
			Log.warn(warning);
			resp.getWriter().println(warning);
			return;
		}
		resp.sendRedirect("/login-admin?goto=%2Fgroup-manager");
	}
	
	public void doMemberManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{	
		
		String addOrRemove = req.getParameter("addOrRemove");
		String leaderOrMember = req.getParameter("leaderOrMember");
		String groupUuid = req.getParameter("groupId");
		String emailsAsString = req.getParameter("emails");
		
		if (addOrRemove != null && leaderOrMember != null && emailsAsString != null && groupUuid != null && UserAPI.loggedIn()){
			Group g = Group.get(groupUuid);
			Person p = Person.get(UserAPI.email());
			if (g != null && p != null && (p.owner || UserAPI.isGoogleAdmin() || g.leaders.contains(p.email))){
				boolean changed = false;
				
				String[] emails = emailsAsString.split(",");
				List<String> trimmedLowerCaseEmails = new ArrayList<String>();
				for (String s : emails){
					trimmedLowerCaseEmails.add(s.toLowerCase().trim());
				}
				
				if (addOrRemove.equals("add")){
					if (leaderOrMember.equals("leader")){
						changed = g.addLeaders(trimmedLowerCaseEmails);
					} else if (leaderOrMember.equals("member")){
						changed = g.addMembers(trimmedLowerCaseEmails);
					}
				} else if (addOrRemove.equals("remove")){
					if (leaderOrMember.equals("leader")){
						changed = g.removeLeaders(trimmedLowerCaseEmails);
					} else if (leaderOrMember.equals("member")){
						changed = g.removeMembers(trimmedLowerCaseEmails);
					}
				}
				
				if (changed){
					g.updateMembershipPage(this);
				}
				
				resp.sendRedirect("/group-manager");
				return;
			}
			
			String permissionsError = "UserAPI [%s] attempted to modify group membership for group [%s][%s], but was blocked for lack of permissions.\n";
			permissionsError = String.format(permissionsError, p.email, g.id, g.name);
			Log.warn(permissionsError);
			resp.getWriter().println(permissionsError);
			return;
		}
		
		String warning = "Incorrect API Call to Group Manager Servlet.  Check Endpoints and make sure you are logged in.\n";
		Log.warn(warning);
		resp.getWriter().println(warning);
		
	}

}
