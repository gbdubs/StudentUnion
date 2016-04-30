package subrandeis.servlet.adv;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.UserAPI;
import subrandeis.entities.Person;

@SuppressWarnings("serial")
public class ManageUsersServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		if (UserAPI.loggedIn()){
			Person currentUser = Person.get(UserAPI.email());
			
			// Sets up information/forms to manage people.
			List<Person> owners = Person.getOwners();
			boolean isOwner = UserAPI.isGoogleAdmin() || owners.contains(currentUser);
			req.setAttribute("isOwner", isOwner);
			req.setAttribute("owners", owners);
			req.setAttribute("admins", Person.getAdmins());
			req.setAttribute("candidates", Person.getCandidates());
			
			// Adds a logout url
			req.setAttribute("logoutUrl", UserAPI.logoutUrl());
			req.setAttribute("currentUser", Person.get(UserAPI.email()));
			
			// Sends the request.
			resp.setContentType("text/html");
			RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/user-management.jsp");	
			jsp.forward(req, resp);	
			
		} else {
			
			// If the user is not logged in, redirect them to the login page, 
			// and set the url so they can come back here when they have logged in.
			resp.sendRedirect("/login-admin?goto=%2Fusers");
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		// Three variables that define this API call. All are required.
		String addOrRemove = req.getParameter("addOrRemove");
		String adminOrOwnerOrCandidate = req.getParameter("adminOrOwnerOrCandidate");
		String personEmail = req.getParameter("email");
		
		if (personEmail == null || addOrRemove == null || adminOrOwnerOrCandidate == null){
			
			// Send an error to the user if API call parameter is missing.
			resp.getWriter().println("One or more parameters not specified. Check your API calls. (ManageUsersServlet)");
			return;
			
		} else {
			
			// Performs the appropriate admin/owner modification based on the call
			// (checking for permission is done inside the Person class, along with a log call)
			if (addOrRemove.equals("add")){
				if (adminOrOwnerOrCandidate.equals("owner")){
					Person.get(personEmail).makeOwner();
				} else if (adminOrOwnerOrCandidate.equals("admin")){
					Person.get(personEmail).makeAdmin();
				} else if (adminOrOwnerOrCandidate.equals("candidate")){
					Person.get(personEmail).makeCandidate();
				}
			} else if (addOrRemove.equals("remove")){
				if (adminOrOwnerOrCandidate.equals("owner")){
					Person.get(personEmail).makeNotOwner();
				} else if (adminOrOwnerOrCandidate.equals("admin")){
					Person.get(personEmail).makeNotAdmin();
				} else if (adminOrOwnerOrCandidate.equals("candidate")){
					Person.get(personEmail).makeNotCandidate();
				}
			}
		}
		
		// Redirects back to the page, now with (if the datastore updates quickly)
		// the corrected/changed names for admins and owners.
		resp.sendRedirect("/users");
	}
	
}
