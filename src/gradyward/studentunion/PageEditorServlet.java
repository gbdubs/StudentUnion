package gradyward.studentunion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PageEditorServlet extends HttpServlet {
	
	private static String editingUrlPrefix = "edit";
	
	private static String linkToHTMLTemplate = "/static/PageEditor/template-site.html";
	private static String linkToContentToolsInitScript = "/static/PageEditor/make-editable.js";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Gets the file path that is trying to be edited via the url pattern 
		String url = req.getRequestURI();
		String filePath = url.substring(url.indexOf("/" + editingUrlPrefix) + (1 + editingUrlPrefix.length()));
		if (!filePath.contains(".html")){
			if (filePath.length() > 0){
				filePath += "/";
			}
			filePath += "index.html";
		}
		
		// Tries to get the current page definition, if it exists. Defaults to a template if it doesn't yet exist.
		String currentPageDef = GithubAPI.getFileText("website", filePath);
		if (currentPageDef == null){
			currentPageDef = getTemplateAsString();
		}
		
		// Places in a script which will instantiate an editor at the end of the body.
		currentPageDef = currentPageDef.replace("</body>", "<script data-editingonly src=\""+linkToContentToolsInitScript+"\"></script></body>");
		
		// Returns the file to the user.
		resp.getWriter().println(currentPageDef);
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Gets the path and content as passed from the form.
		String pagePath = req.getParameter("path");
		String content = req.getParameter("content");
		
		// Logs the request
		System.out.println("Recieved a request to edit ["+pagePath+"]");
		
		// Adjusts the content to create relative urls, given how far down the directory tree the new file is.
		content = makeContentHaveRelativeUrls(pagePath, content);
		
		// Creates the file, returns a brief description of success or failure, depending on which it was.
		resp.setContentType("application/json");
		try {
			GithubAPI.createOrUpdateFile("website", pagePath, "A New Commit at "+(new Date()).toString(), content);
			resp.setStatus(200);
			resp.getWriter().println("{\"success\":1,\"message\":\"Everything worked and the work has been saved.\",\"url\":\""+pagePath+"\"}");
			System.out.println("           	request to edit ["+pagePath+"] WAS SUCCESSFUL");
		} catch (IOException ex){
			resp.setStatus(400);
			resp.getWriter().println("{\"success\":0,\"message\":\""+ex.getMessage()+"\"}");
			System.out.println("           	request to edit ["+pagePath+"] FAILED!: " + ex.getMessage());
		}
	}
	
	
	private String getTemplateAsString() throws IOException{
		ServletContext context = this.getServletContext();
		String filePath =  context.getRealPath(linkToHTMLTemplate);
		InputStreamReader inReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
		StringBuffer sb = new StringBuffer();
	    int charToAdd = 0;
		while ((charToAdd = inReader.read()) != -1) {
	        sb.append((char) charToAdd);
	    }
	    inReader.close();
	    return sb.toString();
	}
	
	private static String makeContentHaveRelativeUrls(String path, String content){
		String replaceWith = "";
		for (char c : path.toCharArray()){
			if (c == '/'){
				replaceWith += "../";
			}
		}
		content = content.replace("href=\"/", "href=\""+replaceWith);	
		content = content.replace("src=\"/", "src=\""+replaceWith);	
		return content;
	}
	
	
}
