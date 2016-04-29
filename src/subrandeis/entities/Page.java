package subrandeis.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import subrandeis.api.ObjectifyAPI;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Page {
	
	static String landingPageToken = "<<<LANDING>>>";
	static Objectify ofy = ObjectifyAPI.ofy();
	
	@Id public String url;
	
	public static void createPage(String url){
		Page p = new Page();
		p.url = modifyPath(url);
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
		while (path.startsWith("/")){
			path = path.substring(1);
		}
		if (!path.endsWith("index.html")){
			if (!path.endsWith("/")){
				path = path + "/";
			}
			path = path + "index.html";
		}
		return path;
	}
	
}