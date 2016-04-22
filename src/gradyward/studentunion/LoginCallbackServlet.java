package gradyward.studentunion;

import gradyward.studentunion.utilities.ObjectifyWrapper;
import gradyward.studentunion.utilities.User;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class LoginCallbackServlet extends HttpServlet{

	private static Objectify ofy = ObjectifyWrapper.ofy();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// This method is always called directly after someone logs in (first time or otherwise).
		
		// It verifies their google provided information (frequenlty it will not have been provided beforehand).
		String googleId = User.id();
		String userEmail = User.email();
		Person p = Person.get(userEmail);
		p.googleId = googleId;
		p.nickname = User.nickname();
		
		// Updates their settings asynchronously.
		ofy.save().entity(p);
		
		// Then it redirects the user to the original source of the login call.
		String redirectTo = req.getParameter("goto");
		resp.sendRedirect(redirectTo);
		
	}
	
}
