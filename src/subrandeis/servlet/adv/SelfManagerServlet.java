package subrandeis.servlet.adv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class SelfManagerServlet extends HttpServlet {
	
	static Objectify ofy = ObjectifyAPI.ofy();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p != null && (p.candidate || p.admin || p.owner || UserAPI.isGoogleAdmin())){
				req.setAttribute("currentUser", Person.get(UserAPI.email()));
				req.setAttribute("logoutUrl", UserAPI.logoutUrl());
				
				// The person whose stuff is going to be edited
				req.setAttribute("person", Person.get(UserAPI.email()));
				
				// Finishes up, sends to the self management page.
				resp.setContentType("text/html");
				RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/self-manager.jsp");
				jsp.forward(req, resp);	
				return;
			}
			Log.warn(String.format("UserAPI [%s] attempted to access the self management page without permission.\n", UserAPI.email()));
		}
		resp.sendRedirect("/login-admin?goto=%2Fself-manager");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String personEmail = req.getParameter("email");
		
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p != null && ((p.email.equals(personEmail)) || p.owner || UserAPI.isGoogleAdmin())){
		
				String personNickname = req.getParameter("nickname");
				String personImageUrl = req.getParameter("imageUrl");
				String personBiography = req.getParameter("biography");
				String personClassYear = req.getParameter("classYear");
				
				Person q = Person.get(personEmail);
				q.nickname = personNickname;
				q.imageUrl = personImageUrl;
				q.biography = personBiography;
				q.classYear = personClassYear;
				
				ofy.save().entity(q);
				
				if (p.equals(q)){
					Log.info(String.format("UserAPI [%s] updated their personal information.\n", personEmail));
				} else {
					Log.info(String.format("UserAPI [%s] updated personal information for user [%s].\n", p.email, q.email));
				}
				resp.sendRedirect("/console");
				return;
			}
			Log.warn(String.format("UserAPI [%s] attempted to update personal information for user [%s], but did not have sufficient permissions.\n", p.email, personEmail));
			resp.sendRedirect("/console");
		} else {
			resp.sendRedirect("/login-admin?goto=%2Fself-manager");
		}
	}
}
