package subrandeis.servlet.basic;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@SuppressWarnings("serial")
public class JSPRenderServlet extends HttpServlet {
	
	private static final String requestFileKey = "jsp-file";
	public static final String responseContentKey = "jsp-response";
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String jspFilePath = req.getAttribute(requestFileKey).toString();
		
		
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(resp) {
			private final StringWriter sw = new StringWriter();

			@Override
			public PrintWriter getWriter() throws IOException {
				return new PrintWriter(sw);
			}

			@Override
			public String toString() {
				return sw.toString();
			}
		};
		
		req.getRequestDispatcher(jspFilePath).include(req, responseWrapper);
		
		String content = responseWrapper.toString();
		
		req.setAttribute(responseContentKey, content);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doGet(req, resp);
	}

	public static String render(String jspFileName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute(requestFileKey, jspFileName);
		
		req.getRequestDispatcher("/jsp-render-servlet").forward(req, resp);
		
		return (String) req.getAttribute(JSPRenderServlet.responseContentKey);
	}
	
}


