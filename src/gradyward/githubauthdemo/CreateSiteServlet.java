package gradyward.githubauthdemo;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CreateSiteServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().println("<html>\n" + 
				"  <head>\n" + 
				"    <title>Page Creator</title>\n" + 
				"  </head>\n" + 
				"\n" + 
				"  <body>\n" + 
				"    <h1>Create a new Page</h1>\n" + 
				"	\n" + 
				"    <form method=\"POST\" action=\"/create-site\">\n" + 
				"    	<input type=\"text\"/>\n" + 
				"    	<button>Submit</button>\n" + 
				"    </form>\n" + 
				"    \n" + 
				"  </body>\n" + 
				"</html>");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String newSiteName = req.getParameter("site-name");
		Human h = Human.getOrCreate();
		if (h != null && newSiteName != null){
			h.addSite(UUID.randomUUID().toString());
		}
		resp.sendRedirect("/success");
	}
}
