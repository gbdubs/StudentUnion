package subrandeis.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtil {

	public static void jsp(String jspFilePath, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher(jspFilePath);
		jsp.forward(req, resp);	
	}
	
}