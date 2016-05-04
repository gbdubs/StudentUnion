package subrandeis.entities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.SecretsAPI;
import subrandeis.api.UserAPI;
import subrandeis.servlet.basic.JSPRenderServlet;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Page {
	
	static String landingPageToken = "<<<LANDING>>>";
	static Objectify ofy = ObjectifyAPI.ofy();
	
	@Id public String url;
	public boolean editable;
	
	public static void createPage(String url, boolean canEdit){
		Page p = new Page();
		p.url = modifyPath(url);
		p.editable = canEdit;
		ofy.save().entity(p).now();
	}
	
	public static void deletePage(String url){
		ofy.delete().type(Page.class).id(modifyPath(url)).now();
	}
	
	public static List<String> getAllPages(){
		List<Page> pages = ofy.load().type(Page.class).list();
		List<String> result = new ArrayList<String>();
		for (Page p : pages){
			if (p.url.equals(landingPageToken)){
				result.add("/");
			} else {
				result.add(p.url);
			}
		}
		Collections.sort(result);
		return result;
	}
	
	public static List<String> getAllEditablePages(){
		List<Page> pages = ofy.load().type(Page.class).list();
		List<String> result = new ArrayList<String>();
		for (Page p : pages){
			if (p.editable){
				if (p.url.equals(landingPageToken)){
					result.add("/");
				} else {
					result.add(p.url);
				}
			}
		}
		Collections.sort(result);
		return result;
	}

	public static Page get(String path) {
		return ofy.load().type(Page.class).id(modifyPath(path)).now();
	}
	
	public static String modifyPath(String path){
		while(path.startsWith("//")){
			path = path.substring(1);
		}
		if (!path.startsWith("/")){
			path = "/" + path;
		}
		if (path.endsWith("index.html")){
			path = path.substring(0, path.indexOf("index.html"));
		}
		while (path.endsWith("/")){
			path = path.substring(0, path.length() - 1);
		}
		if (path.equals("")){
			path = landingPageToken;
		}
		return path;
	}
	
	public static String makeFilePath(String path){
		if (!path.endsWith("index.html")){
			if (!path.endsWith("/")){
				path = path + "/";
			}
			path = path + "index.html";
		}
		while (path.startsWith("/")){
			path = path.substring(1);
		}
		return path;
	}
	
	public boolean getEditable(){
		return this.editable;
	}
	
	public static void updateDirectoryPage(HttpServlet caller, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{

		List<String> pages = Page.getAllPages();
		pages.add("/petitions");
		pages.add("/login-admin");
		pages.add("/login");
		Collections.sort(pages);
		
		Person p = Person.get(UserAPI.email());
		
		req.setAttribute("production", true);
		req.setAttribute("lastEditorEmail", p.email);
		req.setAttribute("lastEditorName", p.nickname);
		String now = (new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm a")).format(new Date());
		req.setAttribute("lastEditorDate", now);
		req.setAttribute("pages", pages);
		
		String completeHtml = JSPRenderServlet.render("/WEB-INF/pages/directory.jsp", req, resp);
		
		String commitMessage = String.format("Directory page updated at [%s] by the user [%s].", now, p.email);
		
		GithubAPI.updateFile(SecretsAPI.GithubRepo, "directory/index.html", commitMessage, completeHtml);
		
		Log.info(commitMessage);
	}
}