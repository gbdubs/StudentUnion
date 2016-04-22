package gradyward.studentunion;

import gradyward.studentunion.utilities.User;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		// Figures out where to go next (defaults to /console if not provided)
		String goToNext = "/console";
		if (req.getParameter("goto") != null){
			goToNext = req.getParameter("goto");
		}
		
		// Creates a login URL which will redirect first to /new-user then to the specified goToNext location.
		String loginUrl = User.loginUrl(goToNext);
		req.setAttribute("loginUrl", loginUrl);
		
		// Displays the owners of the site so that a person requesting access knows who to contact.
		req.setAttribute("owners", Person.getOwners());
		
		// Finishes up, sends to the login page.
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/login.jsp");	
		jsp.forward(req, resp);	
		
	}
	
}
