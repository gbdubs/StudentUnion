package gradyward.studentunion;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().println(
			"<html>" +
			  "<body>" +
				"<form action=\"/page\" method=\"POST\">\n" + 
		   "		<p>PATH = <input name=\"path\" type=\"text\" placeholder=\"path/to/file.txt\"/></p>\n" + 
		   "		<p>Contents = <input name=\"content\" type=\"text\" placeholder=\"path/to/file.txt\"/></p>\n" + 
		   "		<p><button>Submit</button></p>\n" + 
		   "     </form>"
		   + "</body>"
		   + "</head>"
		);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String pagePath = req.getParameter("path");
		String content = req.getParameter("content");
		
		GithubAPI.createOrUpdateFile("website", pagePath, "A New Commit at "+(new Date()).toString(), content);
	}
	
}
