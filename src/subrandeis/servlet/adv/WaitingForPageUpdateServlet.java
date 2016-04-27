package subrandeis.servlet.adv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class WaitingForPageUpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String url = req.getParameter("urlToGoTo");
	
		if (url.contains("index.html")){
			url = url.substring(0, url.indexOf("index.html"));
		}
		req.setAttribute("urlToGoTo", url);
		
		
		// Finishes up, sends to the console page.
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/waiting.jsp");
		jsp.forward(req, resp);	
		return;
	}
}