package subrandeis.servlet.basic;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class LoginCallbackServlet extends HttpServlet{

	private static Objectify ofy = ObjectifyAPI.ofy();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String redirectTo = req.getParameter("goto");
		if (redirectTo == null){
			redirectTo = "/";
		}
		if (UserAPI.loggedIn()){
			// This method is always called directly after someone logs in (first time or otherwise).
			// If the user declines to login, we need to redirect them somewhere else.
			
			// It verifies their google provided information (frequenlty it will not have been provided beforehand).
			String googleId = UserAPI.id();
			String userEmail = UserAPI.email();
			Person p = Person.get(userEmail);
			if ("[No Nickname]".equals(p.nickname)){
				p.googleId = googleId;
				p.nickname = UserAPI.nickname();
				p.admin = UserAPI.isGoogleAdmin();
				p.owner = UserAPI.isGoogleAdmin();
				
				// Updates their settings asynchronously.
				ofy.save().entity(p);
			}
				
			// Then it redirects the user to the original source of the login call.
		} 
		resp.sendRedirect(redirectTo);
	}
	
}
