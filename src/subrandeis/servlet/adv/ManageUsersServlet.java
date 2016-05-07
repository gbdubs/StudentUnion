package subrandeis.servlet.adv;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.Log;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;
import subrandeis.util.EmailUtil;
import subrandeis.util.ServletUtil;

@SuppressWarnings("serial")
public class ManageUsersServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.loggedIn()){
			Person currentUser = Person.get(UserAPI.email());
		
			req.setAttribute("isOwner", UserAPI.isOwner(currentUser));
			req.setAttribute("owners", Person.getOwners());
			req.setAttribute("admins", Person.getAdmins());
			req.setAttribute("candidates", Person.getCandidates());
			
			req.setAttribute("logoutUrl", UserAPI.logoutUrl());
			req.setAttribute("currentUser", currentUser);
			
			ServletUtil.jsp("user-management.jsp", req, resp);
		} else {
			resp.sendRedirect(UserAPI.loginAdminPageUrl("/users", "Candidate, Administrator and Owner Management Page"));
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String addOrRemove = req.getParameter("addOrRemove");
		String adminOrOwnerOrCandidate = req.getParameter("adminOrOwnerOrCandidate");
		String emails = req.getParameter("emails");
		
		if (emails == null || addOrRemove == null || adminOrOwnerOrCandidate == null){
			resp.getWriter().println(Log.WARN("ManageUsersServlet: One or more parameters not specified. Check your API calls. (ManageUsersServlet)"));
		} else {
			List<String> validEmails = EmailUtil.cleanInputListOfEmails(emails);
			List<Person> correspondingPeople = Person.get(validEmails);
			for (Person person : correspondingPeople){
				if (addOrRemove.equals("add")){
					if (adminOrOwnerOrCandidate.equals("owner")){
						person.makeOwner();
					} else if (adminOrOwnerOrCandidate.equals("admin")){
						person.makeAdmin();
					} else if (adminOrOwnerOrCandidate.equals("candidate")){
						person.makeCandidate();
					}
				} else if (addOrRemove.equals("remove")){
					if (adminOrOwnerOrCandidate.equals("owner")){
						person.makeNotOwner();
					} else if (adminOrOwnerOrCandidate.equals("admin")){
						person.makeNotAdmin();
					} else if (adminOrOwnerOrCandidate.equals("candidate")){
						person.makeNotCandidate();
					}
				}
			}
			resp.sendRedirect("/users");
		}
	}
}
