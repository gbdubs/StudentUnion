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
	
	static Objectify ofy = ObjectifyAPI.ofy();
	
	@Id public String url;
	
	public static void createPage(String url){
		Page p = new Page();
		p.url = url;
		ofy.save().entity(p).now();
	}
	
	public static void deletePage(String url){
		ofy.delete().type(Page.class).id(url).now();
	}
	
	public static List<String> getAllPages(){
		List<Page> pages = ofy.load().type(Page.class).list();
		List<String> result = new ArrayList<String>();
		for (Page p : pages){
			result.add(p.url);
		}
		Collections.sort(result);
		return result;
	}
	
}