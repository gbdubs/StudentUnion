package gradyward.studentunion.servlets;

import gradyward.studentunion.Person;
import gradyward.studentunion.utilities.Log;
import gradyward.studentunion.utilities.ObjectifyWrapper;
import gradyward.studentunion.utilities.User;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class SelfManagerServlet extends HttpServlet {
	
	static Objectify ofy = ObjectifyWrapper.ofy();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (User.loggedIn()){
			Person p = Person.get(User.email());
			if (p != null && (p.candidate || p.admin || p.owner || User.isGoogleAdmin())){
				req.setAttribute("currentUser", Person.get(User.email()));
				req.setAttribute("logoutUrl", User.logoutUrl());
				
				// The person whose stuff is going to be edited
				req.setAttribute("person", Person.get(User.email()));
				
				// Finishes up, sends to the self management page.
				resp.setContentType("text/html");
				RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/self-manager.jsp");
				jsp.forward(req, resp);	
				return;
			}
			Log.warn(String.format("User [%s] attempted to access the self management page without permission.\n", User.email()));
		}
		resp.sendRedirect("/login?goto=/self-manager");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String personEmail = req.getParameter("email");
		
		if (User.loggedIn()){
			Person p = Person.get(User.email());
			if (p != null && ((p.email.equals(personEmail)) || p.owner || User.isGoogleAdmin())){
		
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
					Log.info(String.format("User [%s] updated their personal information.\n", personEmail));
				} else {
					Log.info(String.format("User [%s] updated personal information for user [%s].\n", p.email, q.email));
				}
				resp.sendRedirect("/console");
				return;
			}
			Log.warn(String.format("User [%s] attempted to update personal information for user [%s], but did not have sufficient permissions.\n", p.email, personEmail));
			resp.sendRedirect("/console");
		} else {
			resp.sendRedirect("/login?goto=/self-manager");
		}
	}
}
