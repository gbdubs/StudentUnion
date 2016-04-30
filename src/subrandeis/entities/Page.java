package subrandeis.entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.SecretsAPI;
import subrandeis.api.UserAPI;
import subrandeis.servlet.adv.PageEditorServlet;

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
	
	public static void updateDirectoryPage(HttpServlet caller) throws IOException{
		String template = Group.getHtmlTemplate(caller, PageEditorServlet.linkToHTMLTemplate);
		String beginning = template.substring(0, template.indexOf(Group.HTMLTemplateContentStartTag));
		String end = template.substring(template.indexOf(Group.HTMLTemplateContentEndTag)+Group.HTMLTemplateContentEndTag.length());
		
		List<String> pages = Page.getAllPages();
		pages.add("/petitions");
		pages.add("/login-admin");
		pages.add("/login");
		Collections.sort(pages);
		
		StringBuilder html = new StringBuilder();
		
		html.append(beginning);
		
		html.append("<div class=\"content card bg-brandeis-white\">\n" + 
				"	<div class=\"container left-align\">"
				+ "<h1 class=\"light\">Directory</h1>");
		html.append("<h4 class=\"light\">This page lists all of the pages on the Brandeis Student Union Website. Some pages might be harder to find than others, so this page lists them all.</h4>");
		html.append("<br><br>");
		for(String page : pages){
			html.append(String.format("<a href=\"%s\"><h5 class=\"light\">%s</h5></a>\n\n", page, page));
		}
		
		html.append("</div></div>");
		html.append(end);
		
		String finalHtml = PageEditorServlet.makeContentShowLastEditorInformation(html.toString(), Person.get(UserAPI.email()));
		
		String commitMessage = String.format("Directory page updated at [%s] by the user [%s].", (new Date()).toString(), UserAPI.email());
		
		GithubAPI.updateFile(SecretsAPI.WebsiteRepository, "directory/index.html", commitMessage, finalHtml);
		
		Log.info(commitMessage);
		
	}
}