package subrandeis.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
import subrandeis.util.DateUtil;
import subrandeis.util.ServletUtil;

@SuppressWarnings("serial")
public class PageEditorServlet extends HttpServlet {
	
	private static String editingUrlPrefix = "edit";

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
					req.setAttribute("editablePages", Page.getAllEditablePages());
					req.setAttribute("pages", Page.getAllPages());
					req.setAttribute("currentUser", p);
					req.setAttribute("logoutUrl", UserAPI.logoutUrl());
					req.setAttribute("isOwner", p.owner || UserAPI.isGoogleAdmin());
				
					ServletUtil.jsp("page-manager-list.jsp", req, resp);
					return;
				}
			
				// Tests to see if a page has been defined at this editor address.
				Page page = Page.get(filePath);
				if (page == null || !page.editable){
					req.setAttribute("currentUser", p);
					req.setAttribute("logoutUrl", UserAPI.logoutUrl());
					ServletUtil.jsp("page-doesnt-exist.jsp", req, resp);
					return;
				}
				
				String htmlContent;
				// Tries to get the current page definition, if it exists. Defaults to a template if it doesn't yet exist.
				String rawHtml = GithubAPI.getFileText(SecretsAPI.GithubProductionRepo, filePath);
				if (rawHtml == null || rawHtml.contains(GithubAPI.deletedPageHint)){
					htmlContent = getTutorialPage();
				} else {
					String startTag = "<!-- CONTENT BELOW HERE -->";
					String endTag = "<!-- CONTENT ABOVE HERE -->";
					int startIndex = rawHtml.indexOf(startTag) + startTag.length();
					int endIndex = rawHtml.indexOf(endTag);
					htmlContent = rawHtml.substring(startIndex, endIndex);
				}
				
				req.setAttribute("javascriptContent", getPageEditorJavascript());
				req.setAttribute("cssContent", getPageEditorCss());
				req.setAttribute("htmlContent", htmlContent);

				ServletUtil.jsp("page-editor.jsp", req, resp);
			} else {
				resp.getWriter().println("You do not have sufficent permissions to edit this page. Sorry!");
			}
		} else {
			resp.sendRedirect("/login-admin?goto=" + ("/"+editingUrlPrefix+filePath.substring(0, filePath.indexOf("index.html"))).replace("/", "%2F"));
		}
	}
	
	private String getTutorialPage() {
		return  
				"				<h1 class=\"text-right\">Welcome to <b>your</b> new page!</h1>\n" + 
				"				<h2 class=\"text-right\"><i>To start editing, click on the pencil in the upper left hand corner of the screen</i></h2>\n" + 
				"				<p class=\"text-center\">Use the draggable toolbox to edit the simple properties like <i>Italics</i>, <b>Bold</b>, <a href=\"#\">links</a>, and text alignment.</p>\n" + 
				"				<p  class=\"text-left\">Using that toolbox you can also create tables, lists, and much much more!</p>\n" + 
				"				<p  class=\"text-left\">You can insert pictures and pideos by using a link to the image/video. Videos have to come from Youtube or Vimeo. Images can be uploaded wherever, but I would recommend doing it through <a href=\"http://www.imgur.com\">imgur</a>.</p>\n" + 
				"				<p  class=\"text-left\">Finally, you can add styles to your elements using the style frame (change the background, padding, margin, and text color).\n" + 
				"				You can do this by selecting an element (by clicking on it) and then at the bottom of the screen, click on the right-most little button.\n" + 
				"				That will take you into an editor where you can toggle different style choices.\n" + 
				"				There are a lot of choices, but the main ones are:</p>\n" + 
				"				<ol class=\"text-left\">\n" + 
				"					<li><b>Card</b> - Make an element seem like it is a piece of paper, with rounded corners and a slight shaddow around it.</li>\n" + 
				"					<li><b>Button</b> - Buttons look similar to card, but will change color and seem to raise up when they are hovered over.</li>\n" + 
				"					<li><b>Text Color</b> - You can choose from seven font colors, but only one per element.</li>\n" + 
				"					<li><b>Background Color</b> - Note that only some colors are allowed to make sure that the site remains beautiful.</li>\n" + 
				"					<li><b>Padding</b> - Change the amount of space in between the text and the outside edge of the element. This allows you to space out elements differently on the page.</li>\n" + 
				"					<li><b>Margin</b> - Change the amount of space required between elements. This (also) allows you to space out elements differently on the page.</li>\n" + 
				"				</ol>\n" + 
				"				<p>If you want to insert a more advanced element, like divs, code blocks, or javascript, you can do so by selecting the place you would like to put it,\n" + 
				"				then going to the style frame (as described above), and going to the code editor.</p>\n";
	}
	
	private String getPageEditorCss(){
		return "<link data-editingonly rel=\"stylesheet\" type=\"text/css\" href=\"/static/ContentTools/build/content-tools.min.css\">\n";
	}
	
	private String getPageEditorJavascript(){
		return 
				"<script data-editingonly src=\"/static/ContentTools/build/content-tools.js\"></script>\n"+
				"<script data-editingonly src=\"/static/PageEditor/content-tools-init.js\"></script>";
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Person p = Person.get(UserAPI.email());
		boolean admin = UserAPI.isAdmin(p);
		boolean owner = UserAPI.isOwner(p);
		
		if (!admin){
		
			
			return;
		}
		
		if (req.getRequestURI().contains("/page-manager")){
			if (owner){
				String path = req.getParameter("path");
				String addOrDelete = req.getParameter("addOrDelete");
				
				String badChars = path.replaceAll("[A-Za-z0-9\\/\\-]", "");
				
				if (badChars.length() == 0){
					if ("add".equals(addOrDelete)){
						Page.createPage(path, true);
					} else if ("delete".equals(addOrDelete)){
						Page.deletePage(path);
						GithubAPI.deleteFile(Page.makeFilePath(path), String.format("Page [%s] deleted by user [%s].", path, p.email));
					}
					PageEditorServlet.updateDirectoryPage(req, resp);
					resp.sendRedirect("/page-manager");
				} else {
					resp.getWriter().println(Log.WARN("PageEditorServlet: The following characters were not accepted: %s they are not okay as part of a url.", badChars));
				}
			} else {
				resp.getWriter().println(Log.WARN("PageEditorServlet: You are not a page Owner, so you cannot create or delete a page."));
			}
			
		} else {
			
			String pagePath = req.getParameter("path");
			String content = req.getParameter("content");
				
			Log.INFO("PageEditorServlet: Request to edit ["+pagePath+"].");
				
			// Verifies that this page can exist
			Page page = Page.get(pagePath);
			if (page == null || !page.editable){
				resp.setStatus(400);
				resp.getWriter().println("{\"success\":0,\"message\":\""+"This page has not been approved to exist by a site owner, or it is already existing and is not editable."+"\"}");
				Log.ERROR("... PageEditorServlet: request to edit [%s] FAILED!: This page has not been approved to exist by a site owner, or it is already existing and is not editable.",  pagePath);
				return;
			}
			
			req.setAttribute("production", true);
			req.setAttribute("lastEditorEmail", p.email);
			req.setAttribute("lastEditorName", p.nickname);
			req.setAttribute("lastEditorDate", DateUtil.now());
			req.setAttribute("htmlContent", content);
			String completeHtml = JSPRenderServlet.render("page-editor.jsp", req, resp);
				
			boolean successful = GithubAPI.createOrUpdateFile(pagePath, String.format("Updated By %s at %s", p.email, DateUtil.now()), completeHtml);
			if (successful){
				resp.setStatus(200);
				Log.INFO("... PageEditorServlet: Request to edit ["+pagePath+"] SUCCEEDED.");
			} else {	
				resp.setStatus(400);
				Log.ERROR("... PageEditorServlet: Request to edit ["+pagePath+"] FAILED.");
			}
		}
	}

	public static void updateDirectoryPage(HttpServletRequest req, HttpServletResponse resp) {
	
		List<String> pages = Page.getAllPages();
		pages.add("/petitions");
		pages.add("/login-admin");
		pages.add("/login");
		Collections.sort(pages);
			
		Person p = Person.get(UserAPI.email());
			
		req.setAttribute("production", true);
		req.setAttribute("lastEditorEmail", p.email);
		req.setAttribute("lastEditorName", p.nickname);
		req.setAttribute("lastEditorDate", DateUtil.now());
		req.setAttribute("pages", pages);
			
		String completeHtml;
		try {
			completeHtml = JSPRenderServlet.render("directory.jsp", req, resp);
			String commitMessage = String.format("Directory page updated at [%s] by the user [%s].", DateUtil.now(), p.email);
			GithubAPI.createOrUpdateFile("directory/index.html", commitMessage, completeHtml);
			Log.INFO("PageEditorServlet: Updated Directory Page.");
		} catch (ServletException | IOException e) {
			Log.INFO("PageEditorServlet: Unexpected exception in rendering JSP: [%s],", e.getMessage());
		}
	}
}