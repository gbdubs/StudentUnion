package subrandeis.servlet.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.Log;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;
import subrandeis.util.ServletUtil;

@SuppressWarnings("serial")
public class ConsoleServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		if (UserAPI.isAdmin()){
			Person p = Person.get();
			req.setAttribute("currentUser", Person.get(UserAPI.email()));
			req.setAttribute("logoutUrl", UserAPI.logoutUrl());
			req.setAttribute("isCandidate", p.candidate);
			req.setAttribute("isAdmin", UserAPI.isAdmin(p));
			req.setAttribute("isOwner", UserAPI.isOwner(p));
			
			ServletUtil.jsp("console.jsp", req, resp);
		} else {
			Log.WARN("ConsoleServlet: Attempt to access console without permission.");
			resp.sendRedirect(UserAPI.loginAdminPageUrl("/login-admin", "Website Manager Console"));
		}
		
	}
}
