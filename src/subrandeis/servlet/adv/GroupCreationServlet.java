package subrandeis.servlet.adv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Group;
import subrandeis.entities.Person;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class GroupCreationServlet extends HttpServlet {

	static Objectify ofy = ObjectifyAPI.ofy();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p.owner || UserAPI.isGoogleAdmin()){
				
				// Sets the atributes that are rendered.
				req.setAttribute("currentUser", p);
				req.setAttribute("logoutUrl", UserAPI.logoutUrl());
				
				// Finishes up, sends to the console page.
				resp.setContentType("text/html");
				RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/group-creation.jsp");
				jsp.forward(req, resp);	
				return;
			}
			String error = "In rendering the group creation manager, the UserAPI was [%s] and they did not have sufficent permissions.";
			error = String.format(error, p.email);
			Log.warn(error);
			resp.getWriter().println(error);
			return;
		}
		resp.sendRedirect("/login?goto=/group-creation");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p.owner || UserAPI.isGoogleAdmin()){
				String createOrDelete = req.getParameter("createOrDelete");
				if ("create".equals(createOrDelete)){
					String newGroupName = req.getParameter("groupName");
					if (newGroupName == null){
						newGroupName = "[Unnamed Group]";
					}
					Group g = Group.createNewGroup(newGroupName);
					ofy.save().entity(g).now();
					String success = String.format("UserAPI [%s] successfully created group with name [%s] and id [%s].", p.email, g.name, g.id);
					Log.info(success);
					resp.sendRedirect("/console");
					return;
				} else if ("delete".equals(createOrDelete)){
					String groupId = req.getParameter("groupId");
					Group g = Group.get(groupId);
					if (groupId == null || g == null){
						String error = String.format("UserAPI [%s] provided an incorrect API call to delete a group.", p.email);
						resp.getWriter().println(error);
						Log.warn(error);
						return;
					} else {
						String groupName = g.name;
						ofy.delete().entity(g).now();
						String success = String.format("UserAPI [%s] successfully deleted group [%s][%s].\n", p.email, groupName, groupId);
						Log.info(success);
						resp.sendRedirect("/group-creation");
						return;
					}
				} else {
					String error = "Incorrect API call for group creation servlet.";
				    Log.warn(error);
				    resp.getWriter().println(error);
				    return;
				}
			} else {
				String error = "UserAPI [%s] does not have sufficent permissions to edit group existence.";
				error = String.format(error, p.email);
				resp.getWriter().println(error);
				Log.warn(error);
				return;
			}
		} else {
			resp.sendRedirect("/login?goto=/group-creation");
		}
	}
}
