package gradyward.studentunion;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@SuppressWarnings("serial")
public class ElectionsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (upcomingElections == -1){
			ElectionsDatum ed = ofy.load().type(ElectionsDatum.class).id(key).now();
			upcomingElections = ed.upcoming ? 0 : 1;
		}
		resp.getWriter().println(upcomingElections);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ElectionsDatum ed = ofy.load().type(ElectionsDatum.class).id(key).now();
		int upcoming = Integer.parseInt(req.getParameter("elections-upcoming"));
		ed.upcoming = upcoming > 0;
		upcomingElections = ed.upcoming ? 0 : 1;
		ofy.save().entity(ed);
	}
	
	private static String key = "SINGLETON";
	private static Objectify ofy = ObjectifyWrapper.ofy();
	
	int upcomingElections = -1;
	
	@Entity
	public class ElectionsDatum{
		@Id String id = key;
		boolean upcoming = false;
	}
	
}
