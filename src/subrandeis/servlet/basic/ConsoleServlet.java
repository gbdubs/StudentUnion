package subrandeis.servlet.basic;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.Log;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;

@SuppressWarnings("serial")
public class ConsoleServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p != null && (p.candidate || p.admin || p.owner || UserAPI.isGoogleAdmin())){
				
				// Gives personalized page (showing only what can be edited by each)
				req.setAttribute("currentUser", Person.get(UserAPI.email()));
				req.setAttribute("logoutUrl", UserAPI.logoutUrl());
				req.setAttribute("isCandidate", p.candidate);
				req.setAttribute("isAdmin", p.admin || p.owner || UserAPI.isGoogleAdmin());
				req.setAttribute("isOwner", p.owner || UserAPI.isGoogleAdmin());
				
				// Finishes up, sends to the console page.
				resp.setContentType("text/html");
				RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/console.jsp");
				jsp.forward(req, resp);	
				return;
			}
			Log.warn(String.format("UserAPI [%s] attempted to access the console without permission.\n", UserAPI.email()));
		}
		// If no permission to see page, redirects to the login page.
		resp.sendRedirect("/login?goto=/console");
	}
}
