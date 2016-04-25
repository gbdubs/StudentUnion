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
			if (g != null && (p.owner || UserAPI.isGoogleAdmin() || g.leaders.contains(p.email))){
				
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
			String error = "There was an error in rendering the group manager for the group requested with id [%s]. The UserAPI was [%s].";
			error = String.format(error, req.getParameter("groupId"), UserAPI.email());
			Log.warn(error);
			resp.getWriter().println(error);
			return;
		}
		resp.sendRedirect("/login?goto=/group-manager");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String manage = req.getParameter("manage");
		
		if (manage == null){
			resp.getWriter().println("Missing parameter [manage].\n");
		}
		
		if (manage.equals("nameOrPageUrl")){
			doNamePageUrlManagement(req, resp);
		} else if (manage.equals("roles")){
			doRoleManagement(req, resp);
		} else if (manage.equals("members")){
			doMemberManagement(req, resp);
		} else {
			resp.getWriter().println("Incorrect value for parameter [manage] passed.");
		}		
	}
	
	public void doNamePageUrlManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if (!UserAPI.loggedIn()){
			String groupId = req.getParameter("groupId");
			Person p = Person.get(UserAPI.email());
			Group g = Group.get(groupId);

			if (g != null && p != null && (UserAPI.isGoogleAdmin() || p.owner || g.leaders.contains(p.email))){
				String name = req.getParameter("name");
				String pageURL = req.getParameter("url");
				if (name != null && pageURL != null){
					g.name = name;
					g.pageUrl = pageURL;
					ofy.save().entity(g);
					Log.info(String.format("Successfully updated the name and page URL of group [%s] to [%s] and [%s] respectively.\n", g.id, g.name, g.pageUrl));	
					resp.sendRedirect("/group-manager");
					return;
				} 
				resp.getWriter().println("Either the name or pageURL was not passed properly. Check your API Call.\n");
				return;
			}
			Log.warn(String.format("Something is wrong in the name and page url management of the group. The incorrect call was made by uaser [%s]\n", UserAPI.email()));
			return;
		}
		resp.sendRedirect("/login?goto=group-manager");
	}
	
	public void doRoleManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		if (!UserAPI.loggedIn()){
		
			String groupId = req.getParameter("groupId");
			Person p = Person.get(UserAPI.email());
			Group g = Group.get(groupId);

			if (g != null && p != null && (UserAPI.isGoogleAdmin() || p.owner || g.leaders.contains(p.email))){
				int roleIndex = 1;
				Map<String, String> roles = new HashMap<String, String>();
				while (req.getParameter("role" + roleIndex) != null){
					String emailX = req.getParameter("email" + roleIndex);
					String roleX = req.getParameter("role" + roleIndex);
					roles.put(emailX, roleX);
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
		resp.sendRedirect("/login?goto=/group-manager");
	}
	
	public void doMemberManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{	
		
		String addOrRemove = req.getParameter("addOrRemove");
		String leaderOrMember = req.getParameter("leaderOrMember");
		String groupUuid = req.getParameter("uuid");
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
