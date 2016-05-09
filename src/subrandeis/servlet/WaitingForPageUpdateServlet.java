package subrandeis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.util.ServletUtil;

@SuppressWarnings("serial")
public class WaitingForPageUpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String urlToGoTo = req.getParameter("urlToGoTo");
	
		if (urlToGoTo.contains("index.html")){
			urlToGoTo = urlToGoTo.substring(0, urlToGoTo.indexOf("index.html"));
		}
		
		req.setAttribute("urlToGoTo", urlToGoTo);
		
		ServletUtil.jsp("waiting.jsp", req, resp);
		
	}
}