package subrandeis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;
import subrandeis.util.ServletUtil;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class SelfManagerServlet extends HttpServlet {
	
	static Objectify ofy = ObjectifyAPI.ofy();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (UserAPI.isCandidate()){
			req.setAttribute("currentUser", Person.get(UserAPI.email()));
			req.setAttribute("logoutUrl", UserAPI.logoutUrl());
			
			//TODO The person whose stuff is going to be edited (might change this to allow owners to edit anyone)
			req.setAttribute("person", Person.get(UserAPI.email()));
				
			ServletUtil.jsp("self-manager.jsp", req, resp);
		} else {
			resp.sendRedirect(UserAPI.loginAdminPageUrl("/self-manager", "Biography and Personal Details Page"));
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String personEmail = req.getParameter("email");
		if (UserAPI.isOwner() || UserAPI.email().equals(personEmail)){
		
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
			Log.INFO("SelfManagerServlet: updated personal information for user [%s].", q.email);
			resp.sendRedirect("/console");
		} else {
			resp.sendRedirect(UserAPI.loginAdminPageUrl("/self-manager", "Biography and Personal Details Page"));
		}
	}
}
