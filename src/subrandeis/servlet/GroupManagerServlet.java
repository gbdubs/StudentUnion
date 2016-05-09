package subrandeis.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Group;
import subrandeis.entities.Page;
import subrandeis.entities.Person;
import subrandeis.util.DateUtil;
import subrandeis.util.EmailUtil;
import subrandeis.util.ServletUtil;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class GroupManagerServlet extends HttpServlet{

	static Objectify ofy = ObjectifyAPI.ofy();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		Group g = Group.get(req.getParameter("groupId"));
		if (g != null && isGroupLeader(g)){
			req.setAttribute("group", g);
			
			ServletUtil.jsp("group-manager.jsp", req, resp);
		} else if (UserAPI.isAdmin()) {
			req.setAttribute("groups", Group.getAllGroups());
			
			ServletUtil.jsp("group-manager-list.jsp", req, resp);
		} else {
			resp.getWriter().println(Log.WARN("GroupManagerServlet: There was an error in rendering the group manager for the group requested with id [%s].", req.getParameter("groupId")));
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String manage = req.getParameter("manage");
		
		if (manage == null){
			resp.getWriter().println("Missing parameter [manage].\n");
		} else if ("nameOrPageUrlOrDescription".equals(manage)){
			doNamePageUrlDescriptionManagement(req, resp);
			GroupCreationServlet.doUpdateGroupsPage(req, resp);
		} else if ("roles".equals(manage)){
			doRoleManagement(req, resp);
		} else if ("members".equals(manage)){
			doMemberManagement(req, resp);
		} else if ("updatePage".equals(manage)){
			doMembershipPageUpdate(req, resp);
		} else {
			resp.getWriter().println("Incorrect value for parameter [manage] passed.");
		}		
	}
	
	private void doMembershipPageUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Group g = Group.get(req.getParameter("groupId"));
		if (isGroupMember(g)){
			updateMembershipPage(g, req, resp);
			resp.sendRedirect("/group-manager?groupId="+g.id);
		} else {	
			resp.getWriter().println(Log.WARN("GroupManagerServlet: Insufficent permissions to refresh membership page in group [%s][%s].", g.id, g.name));
		}
	}

	public void doNamePageUrlDescriptionManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Group g = Group.get(req.getParameter("groupId"));
		if (isGroupLeader(g)){
			String name = req.getParameter("name");
			String pageURL = req.getParameter("pageUrl");
			String description = req.getParameter("description");
			
			name = name == null ? "[Unnamed Group]" : name;
			pageURL = pageURL == null ? Group.undefinedPageUrl : pageURL;
			
			g.name = name;
			g.pageUrl = pageURL;
			g.description = description;
			ofy.save().entity(g);
			
			Log.INFO("GroupManagerServlet: Updated the name and page URL and description of group [%s] to [%s], [%s] and [%s] respectively.", g.id, g.name, g.pageUrl, g.description);	
			resp.sendRedirect("/group-manager?groupId="+g.id);
		} else { 
			resp.getWriter().println(Log.WARN("GroupManagerServlet: Insufficent permissions to update gropu information for group [%s][%s].", g.id, g.name));
		}
	}
	
	public void doRoleManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Group g = Group.get(req.getParameter("groupId"));
		if (isGroupLeader(g)){
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
			Log.INFO("GroupManagerServlet: Updated the roles for group [%s][%s] to [%s].", g.id, g.name, roles.toString());
			resp.sendRedirect("/group-manager");
		} else {
			resp.getWriter().println(Log.WARN("GroupManagerServlet: Insufficent permissions to update roles in group [%s][%s].", g.id, g.name));
		}
	}
	
	public void doMemberManagement(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{	
		Group g = Group.get(req.getParameter("groupId"));
		if (isGroupLeader(g)){
			String addOrRemove = req.getParameter("addOrRemove");
			String leaderOrMember = req.getParameter("leaderOrMember");
			String emailsAsString = req.getParameter("emails");
			
			boolean changed = false;
			
			List<String> emails = EmailUtil.cleanInputListOfEmails(emailsAsString);
			
			if (addOrRemove.equals("add")){
				if (leaderOrMember.equals("leader")){
					changed = g.addLeaders(emails);
				} else if (leaderOrMember.equals("member")){
					changed = g.addMembers(emails);
				}
			} else if (addOrRemove.equals("remove")){
				if (leaderOrMember.equals("leader")){
					changed = g.removeLeaders(emails);
				} else if (leaderOrMember.equals("member")){
					changed = g.removeMembers(emails);
				}
			}
			
			if (changed){
				updateMembershipPage(g, req, resp);
			}
			
			resp.sendRedirect("/group-manager?groupId="+g.id);
		} else {
			resp.getWriter().println(Log.WARN("GroupManagerServlet: Insufficent permissions to change member management in group [%s][%s].", g.id, g.name));
		}		
	}

	public static void updateMembershipPage(Group group, HttpServletRequest req, HttpServletResponse resp){
		try {
			if (!group.pageUrl.equals(Group.undefinedPageUrl)){
				if (Page.get(group.pageUrl) == null){
					Page.createPage(group.pageUrl, true);
				}
				if (Page.get(group.pageUrl+"/members") == null){
					Page.createPage(group.pageUrl + "/members", false);
				}
				
				String membershipPageUrl = group.pageUrl + "/members/index.html";
				while (membershipPageUrl.startsWith("/")){
					membershipPageUrl = membershipPageUrl.substring(1);
				}
			
				List<String> allPeople = group.getAllMembers();
				
				Map<String, Person> inDB = Group.ofy.load().type(Person.class).ids(allPeople);
				List<Person> people = new ArrayList<Person>();
				
				for (String s : allPeople){
					Person p = inDB.get(s);
					if (p == null){
						p = Person.get(s);
					}
					people.add(p);
				}
				
				Person p = Person.get();
				
				req.setAttribute("production", true);
				req.setAttribute("lastEditorEmail", p.email);
				req.setAttribute("lastEditorName", p.nickname);
				req.setAttribute("roles", group.roles);
				req.setAttribute("lastEditorDate", DateUtil.now());
				req.setAttribute("group", group);
				req.setAttribute("people", people);
				
				System.out.println("IS COMMITTED? "+resp.isCommitted());
				
				String pageHtml = JSPRenderServlet.render("group-member-page.jsp", req);
			
				System.out.println("IS COMMITTED? "+resp.isCommitted());
				
				String commitMessage = String.format("Membership page updated at %s.", (new Date()).toString());
				
				GithubAPI.createOrUpdateFile(membershipPageUrl, commitMessage, pageHtml);
				
				Log.INFO("GroupManagerServlet: Updated membership page for group [%s][%s] successfully", group.name, group.id);
			} else {
				resp.getWriter().println(Log.WARN(String.format("GroupManagerServlet: No pageURL defined for group [%s][%s], so it was not updated.", group.id, group.name)));
			}
		} catch (IOException | ServletException ioe){
			try {
				resp.getWriter().println(Log.ERROR("GroupManagerServlet: Error in updating membership page: [%s]", ioe.getMessage()));
			} catch (IOException e) {
				Log.ERROR("GroupManagerServlet: Error Getting Print Writer. This is severe.");
			}
		}
	}
	
	private static boolean isGroupLeader(Group g){
		if (g == null){
			return false;
		}
		if (UserAPI.isOwner()){
			return true;
		}
		String userEmail = UserAPI.email();
		if (userEmail != null && g.leaders.contains(userEmail)){
			return true;
		}
		return false;
	}
	
	private static boolean isGroupMember(Group g){
		if (g == null){
			return false;
		}
		if (UserAPI.isOwner()){
			return true;
		}
		String userEmail = UserAPI.email();
		if (userEmail != null && g.leaders.contains(userEmail)){
			return true;
		}
		if (userEmail != null && g.members.contains(userEmail)){
			return true;
		}
		return false;
	}
}
