package subrandeis.servlet.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.UserAPI;
import subrandeis.entities.Person;
import subrandeis.util.ServletUtil;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (req.getRequestURI().contains("admin")){
		
			String goToNext = req.getParameter("goto");
			goToNext = goToNext == null ? "/console" : goToNext;
			
			req.setAttribute("loginUrl", UserAPI.loginUrl(goToNext));
			req.setAttribute("owners", Person.getOwners());
			req.setAttribute("admins", Person.getAdmins());
			req.setAttribute("candidates", Person.getCandidates());
			
			ServletUtil.jsp("login-admin.jsp", req, resp);	
			
		} else {
			
			String goToNext = req.getParameter("goto");
			goToNext = goToNext == null ? "/petitions" : goToNext;

			req.setAttribute("loginUrl", UserAPI.loginUrl(goToNext));
			
			ServletUtil.jsp("login-normal.jsp", req, resp);	
			
		}
	}
}
