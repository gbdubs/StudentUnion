package gradyward.githubauthdemo;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;

@Entity
public class Page {

	@Id String pageId;
	String pageName;
	String siteId;
	String content;
	String exactUrl;
	
	public static Page getOrCreate(String siteId, String pageId){
		Page p = ofy().load().type(Page.class).id(siteId).now();
		if (p == null){
			p = new Page();
			p.pageId = pageId;
			p.pageName = "Untitled Page";
			p.siteId = siteId;
			p.content = "<h1>NEW PAGE</h1>";
		}
		return p;
	}
	
	@OnSave void savePageToGithub() { 
		
		
		
		
	}
}
