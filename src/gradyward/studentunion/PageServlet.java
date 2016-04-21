package gradyward.studentunion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String url = req.getRequestURI();
		url = url.substring(url.indexOf("/page/"));
		url = url+"/index.html";
		
		String currentPageDef = GithubAPI.getFileText("website", url);
		
		if (currentPageDef == null){
			currentPageDef = getTemplateAsString();
		}
		
		currentPageDef = currentPageDef.replace("</body>", "<script data-editingonly src=\"/static/make-editable.js\"></script></body>");
		
		resp.getWriter().println(currentPageDef);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String pagePath = req.getParameter("path");
		String content = req.getParameter("content");
		
		GithubAPI.createOrUpdateFile("website", pagePath, "A New Commit at "+(new Date()).toString(), content);
	}
	
	private String getTemplateAsString() throws IOException{
		ServletContext context = this.getServletContext();
		String filePath =  context.getRealPath("/static/boilerplate.html");
		InputStreamReader inReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
		StringBuffer sb = new StringBuffer();
	    int charToAdd = 0;
		while ((charToAdd = inReader.read()) != -1) {
	        sb.append((char) charToAdd);
	    }
	    inReader.close();
	    return sb.toString();
	}
	
	
}
