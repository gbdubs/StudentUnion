package subrandeis.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtil {

	public static String jspRootDirectory = "/WEB-INF/pages/";

	public static void jsp(String jspFilePath, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher(jspRootDirectory + jspFilePath);
		jsp.forward(req, resp);	
	}

	public static String renderJSPandReturn(String jspFileName, HttpServletRequest req) throws ServletException, IOException{
		ServletUtil.DummyHttpServletResponse dhsr = new ServletUtil.DummyHttpServletResponse();
		
		req.getRequestDispatcher(jspRootDirectory + jspFileName).include(req, dhsr);
		
		String content = dhsr.toString();
		
		return content;
	}

	public static class DummyHttpServletResponse implements HttpServletResponse {
		private final StringWriter sw = new StringWriter();
	
		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(sw);
		}
	
		@Override
		public String toString() {
			return sw.toString();
		}
	
		public void flushBuffer() throws IOException {}
		public int getBufferSize() {return 0;}
		public String getCharacterEncoding() {return null;}
		public String getContentType() {return null;}
		public Locale getLocale() {return null;}
		public ServletOutputStream getOutputStream() throws IOException {return null;}
		public boolean isCommitted() {return false;}
		public void reset() {}
		public void resetBuffer() {}
		public void setBufferSize(int arg0) {}
		public void setCharacterEncoding(String arg0) {}
		public void setContentLength(int arg0) {}
		public void setContentType(String arg0) {}
		public void setLocale(Locale arg0) {}
		public void addCookie(Cookie arg0) {}
		public void addDateHeader(String arg0, long arg1) {}
		public void addHeader(String arg0, String arg1) {}
		public void addIntHeader(String arg0, int arg1) {}
		public boolean containsHeader(String arg0) {return false;}
		public String encodeRedirectURL(String arg0) {return null;}
		public String encodeRedirectUrl(String arg0) {return null;}
		public String encodeURL(String arg0) {return null;}
		public String encodeUrl(String arg0) {return null;}
		public void sendError(int arg0) throws IOException {}
		public void sendError(int arg0, String arg1) throws IOException {}
		public void sendRedirect(String arg0) throws IOException {}
		public void setDateHeader(String arg0, long arg1) {}
		public void setHeader(String arg0, String arg1) {}
		public void setIntHeader(String arg0, int arg1) {}
		public void setStatus(int arg0) {}
		public void setStatus(int arg0, String arg1) {}
	}
	
}
