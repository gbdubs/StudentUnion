package subrandeis.servlet.adv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Group;
import subrandeis.entities.Person;
import subrandeis.util.ServletUtil;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class GroupCreationServlet extends HttpServlet {

	static Objectify ofy = ObjectifyAPI.ofy();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.isOwner()){
			req.setAttribute("currentUser", Person.get());
			req.setAttribute("logoutUrl", UserAPI.logoutUrl());
			req.setAttribute("groups", Group.getAllGroups());
				
			ServletUtil.jsp("group-creation.jsp", req, resp);
		} else {
			resp.sendRedirect(UserAPI.loginAdminPageUrl("/group-creation", "Group Creation and Deletion"));
		}		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if(UserAPI.isOwner()){
			String action = req.getParameter("createOrDelete");
			if ("create".equals(action)){
				String newGroupName = req.getParameter("groupName");
				newGroupName = newGroupName == null ? "[Unnamed Group" : newGroupName;
				Group g = Group.createNewGroup(newGroupName);
				GroupManagerServlet.updateMembershipPage(g, req, resp);
				resp.sendRedirect("/group-editor?groupId="+g.id);
				
			} else if ("delete".equals(action)){
				Group g = Group.get(req.getParameter("groupId"));
				if (g == null){
					String error = "GroupCreationServlet: Incorrect GroupID For Deletion Request.";
					resp.getWriter().println(error);
					Log.WARN(error);
				} else {
					Group.deleteGroup(g.id);
					resp.sendRedirect("/group-creation");
				}
			}
		} else {
			resp.sendRedirect(UserAPI.loginAdminPageUrl("/group-creation", "Group Creation and Deletion"));
		}
	}
}
