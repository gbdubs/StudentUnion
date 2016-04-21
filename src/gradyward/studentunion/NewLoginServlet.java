package gradyward.studentunion;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class NewLoginServlet extends HttpServlet{

	private static Objectify ofy = ObjectifyWrapper.ofy();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String googleId = User.id();
		String userEmail = User.email();
		Person p = Person.get(userEmail);
		p.googleId = googleId;
		ofy.save().entity(p);
		
		String redirectTo = req.getParameter("goto");
		resp.sendRedirect(redirectTo);
	}
	
}
