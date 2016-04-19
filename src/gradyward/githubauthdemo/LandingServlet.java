package gradyward.githubauthdemo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LandingServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter w = resp.getWriter();
		
		w.println("<html>\n" + 
				"  <head>\n" + 
				"    <title>Github Demo</title>\n" + 
				"  </head>\n" + 
				"\n" + 
				"  <body>\n" + 
				"    <h1>Welcome User!</h1>\n" + 
				"	\n");

		if (User.loggedIn()){
			Human h = Human.getOrCreate();
			for (Site s : h.getSites()){
				w.printf("<p><a href=\"%s\">%s</a></p>\n", "/site/"+s.siteId, s.siteName);
			}
			w.println("<p><a href=\"/create-site\">Create New Site</a></p>");
			
			w.printf("<p><a href=\"%s\">Logout</a></p>\n", User.logoutUrl());
		} else {
			w.printf("<p><a href=\"%s\">Login</a></p>\n", User.loginUrl("/home"));
		}
		
		w.printf("<p><a href=\"%s\">Github Login</a></p>\n", "/github-step-one");
		w.println("  </body>\n" + 
				"</html>\n");
		
		
	}

}
