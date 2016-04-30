package subrandeis.servlet.basic;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.UserAPI;
import subrandeis.entities.Person;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (req.getRequestURI().contains("admin")){
		
			// Figures out where to go next (defaults to /console if not provided)
			String goToNext = "/console";
			if (req.getParameter("goto") != null){
				goToNext = req.getParameter("goto");
			}
			
			// Creates a login URL which will redirect first to /new-user then to the specified goToNext location.
			String loginUrl = UserAPI.loginUrl(goToNext);
			req.setAttribute("loginUrl", loginUrl);
			
			// Displays the owners of the site so that a person requesting access knows who to contact.
			req.setAttribute("owners", Person.getOwners());
			req.setAttribute("admins", Person.getAdmins());
			req.setAttribute("candidates", Person.getCandidates());
			
			// Finishes up, sends to the administrator login page.
			resp.setContentType("text/html");
			RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/login-admin.jsp");	
			jsp.forward(req, resp);	
			
		} else {
			
			// Assumes that the place to go next is the Petitions page
			String goToNext = "/petitions";
			if (req.getParameter("goto") != null){
				goToNext = req.getParameter("goto");
			}
			
			// Creates a login URL which will redirect first to /new-user then to the specified goToNext location.
			String loginUrl = UserAPI.loginUrl(goToNext);
			req.setAttribute("loginUrl", loginUrl);
			
			// Finishes up, sends to the plebian login page.
			resp.setContentType("text/html");
			RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/login-normal.jsp");	
			jsp.forward(req, resp);	
			
		}
	}
	
}
