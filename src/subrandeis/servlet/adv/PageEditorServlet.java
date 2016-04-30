package subrandeis.servlet.adv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.SecretsAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Page;
import subrandeis.entities.Person;

@SuppressWarnings("serial")
public class PageEditorServlet extends HttpServlet {
	
	private static String editingUrlPrefix = "edit";
	
	public static String linkToHTMLTemplate = "/static/PageEditor/template-site.html";
	public static String linkToContentToolsInitScript = "/static/PageEditor/make-editable.js";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		// Gets the file path that is trying to be edited via the url pattern
		String url = req.getRequestURI();
		String filePath = url.substring(url.indexOf("/" + editingUrlPrefix) + (1 + editingUrlPrefix.length()));
		if (!filePath.contains(".html")){
			if (filePath.length() > 0){
				filePath += "/";
			}
			filePath += "index.html";
		}
		
		if (UserAPI.loggedIn()){
	
			Person p = Person.get(UserAPI.email());
		
			if (p.admin || p.owner || UserAPI.isGoogleAdmin()){
				
				if (url.contains("/page-manager")){
					req.setAttribute("pages", Page.getAllPages());
					req.setAttribute("currentUser", p);
					req.setAttribute("logoutUrl", UserAPI.logoutUrl());
					req.setAttribute("isOwner", p.owner || UserAPI.isGoogleAdmin());
					
					// Finishes up, sends to the console page.
					resp.setContentType("text/html");
					RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/page-manager-list.jsp");
					jsp.forward(req, resp);	
					return;
				}
			
				// Tests to see if a page has been defined at this editor address.
				Page page = Page.get(filePath);
				if (page == null){
					req.setAttribute("currentUser", p);
					req.setAttribute("logoutUrl", UserAPI.logoutUrl());
					resp.setContentType("text/html");
					RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/page-doesnt-exist.jsp");
					jsp.forward(req, resp);	
					return;
				}
				
				// Tries to get the current page definition, if it exists. Defaults to a template if it doesn't yet exist.
				String currentPageDef = GithubAPI.getFileText(SecretsAPI.WebsiteRepository, filePath);
				if (currentPageDef == null || currentPageDef.contains("window.location.replace('/404');")){
					currentPageDef = getTemplateAsString();
				}
				
				// Places in a script which will instantiate an editor at the end of the body.
				currentPageDef = currentPageDef.replace("</body>", "<script data-editingonly src=\""+linkToContentToolsInitScript+"\"></script></body>");
				
				// Returns the file to the user.
				resp.getWriter().println(currentPageDef);
				
			} else {
				resp.getWriter().println("You do not have sufficent permissions to edit this page. Sorry!");
			}
		} else {
			resp.sendRedirect("/login-admin?goto=" + ("/"+editingUrlPrefix+filePath.substring(0, filePath.indexOf("index.html"))).replace("/", "%2F"));
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		if (UserAPI.loggedIn()){
		
			Person p = Person.get(UserAPI.email());
			
			if (req.getRequestURI().contains("/page-manager")){
				if (p.owner || UserAPI.isGoogleAdmin()){
					String path = req.getParameter("path");
					String addOrDelete = req.getParameter("addOrDelete");
					
					String badChars = path.replaceAll("[A-Za-z0-9\\/\\-]", "");
					
					if (badChars.length() == 0){
						if ("add".equals(addOrDelete)){
							Page.createPage(path);
						} else if ("delete".equals(addOrDelete)){
							Page.deletePage(path);
							String commitMessage = String.format("Page [%s] deleted by user [%s].", path, p.email);
							GithubAPI.deleteFile(SecretsAPI.WebsiteRepository, Page.makeFilePath(path), commitMessage);
							Log.info(commitMessage);
						}
					} else {
						resp.getWriter().println("The following characters were not accepted: "+badChars+" they are not okay as part of a url.");
						return;
					}
				}
				resp.sendRedirect("/page-manager");
				return;
			}
			
			if (p.admin || p.owner || UserAPI.isGoogleAdmin()){
			
				// Gets the path and content as passed from the form.
				String pagePath = req.getParameter("path");
				String content = req.getParameter("content");
				
				// Logs the request
				Log.info("Recieved a request to edit ["+pagePath+"] from user ["+p.email+"]\n");
				
				// Adjusts the content to create relative urls, given how far down the directory tree the new file is.
				content = makeContentHaveRelativeUrls(pagePath, content);
				
				// Adds in data to reflect who the last person to make the change was, and when it was made.
				content = makeContentShowLastEditorInformation(content, p);
				
				// Creates the file, returns a brief description of success or failure, depending on which it was.
				resp.setContentType("application/json");
				try {
					GithubAPI.createOrUpdateFile(SecretsAPI.WebsiteRepository, pagePath, "A New Commit at "+(new Date()).toString(), content);
					
					resp.setStatus(200);
					resp.getWriter().println("{\"success\":1,\"message\":\"Everything worked and the work has been saved.\",\"url\":\""+pagePath+"\"}");
					Log.info("           	request to edit ["+pagePath+"] WAS SUCCESSFUL\n");
				} catch (IOException ex){
					resp.setStatus(400);
					resp.getWriter().println("{\"success\":0,\"message\":\""+ex.getMessage()+"\"}");
					Log.error("           	request to edit ["+pagePath+"] FAILED!: " + ex.getMessage());
				}
			}
		}
	}
	
	public static String makeContentShowLastEditorInformation(String content, Person p) {
		content = content.replace("[LAST EDITOR EMAIL]", p.email);
		content = content.replace("[LAST EDITOR NICKNAME]", p.nickname);
		DateFormat df = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm a");
		content = content.replace("[LAST EDITOR DATE]", df.format(new Date()));
		return content;
	}

	public String getTemplateAsString() throws IOException{
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
	
	public static String makeContentHaveRelativeUrls(String path, String content){
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
