package subrandeis.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;

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
		Log.INFO("Page: Created page with URL [%s] modified to [%s].", url, p.url);
	}
	
	public static void deletePage(String url){
		String modifiedPath = modifyPath(url);
		Page p = ofy.load().type(Page.class).id(modifiedPath).now();
		if (p == null){
			Log.WARN("Page: Attempt to delete non-existent page with URL [%s] modified to [%s].", url, modifiedPath);
		} else {
			ofy.delete().entity(p).now();
			Log.INFO("Page: Deleted page with URL [%s] modified to [%s].", url, modifiedPath);
		}
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
		if (path == null){
			return null;
		}
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
}