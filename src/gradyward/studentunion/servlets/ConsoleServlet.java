package gradyward.studentunion.servlets;

import gradyward.studentunion.Person;
import gradyward.studentunion.utilities.Log;
import gradyward.studentunion.utilities.User;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ConsoleServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (User.loggedIn()){
			Person p = Person.get(User.email());
			if (p != null && (p.candidate || p.admin || p.owner || User.isGoogleAdmin())){
				
				// Gives personalized page (showing only what can be edited by each)
				req.setAttribute("currentUser", Person.get(User.email()));
				req.setAttribute("logoutUrl", User.logoutUrl());
				req.setAttribute("isCandidate", p.candidate);
				req.setAttribute("isAdmin", p.admin || p.owner || User.isGoogleAdmin());
				req.setAttribute("isOwner", p.owner || User.isGoogleAdmin());
				
				// Finishes up, sends to the console page.
				resp.setContentType("text/html");
				RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/console.jsp");
				jsp.forward(req, resp);	
				return;
			}
			Log.warn(String.format("User [%s] attempted to access the console without permission.\n", User.email()));
		}
		// If no permission to see page, redirects to the login page.
		resp.sendRedirect("/login?goto=/console");
	}
}
