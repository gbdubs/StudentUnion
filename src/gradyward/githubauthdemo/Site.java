package gradyward.githubauthdemo;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;

@Entity
public class Site {
	static Objectify ofy = ObjectifyWrapper.ofy();
	
	@Id String siteId;
	String githubBaseUrl;
	String githubAuthenticationToken;
	String siteName;
	Map<String, Key<Page>> urlPatterns;
	List<Key<Human>> owners;
	boolean repoExists;

	public static Site getOrCreate(String siteId){
		Site s = ofy().load().type(Site.class).id(siteId).now();
		if (s == null){
			s = new Site();
			s.siteId = "siteId";
			s.githubBaseUrl = "NOT DEFINED";
			s.githubAuthenticationToken = null;
			s.siteName = "UN-NAMED";
			s.urlPatterns = new HashMap<String, Key<Page>>();
			s.owners = new ArrayList<Key<Human>>();
			s.repoExists = false;
		}
		return s;
	}
	
	public void addPage(String urlPattern, String pageId){
		Page p = Page.getOrCreate(this.siteId, pageId);
		this.urlPatterns.put(urlPattern, Key.create(p));
		p.exactUrl = githubBaseUrl + urlPattern;
		ofy().save().entities(this, p);
	}
	
	@OnSave void checkToSeeIfRepoExists(){
		String authToken = TradeSecrets.currentToken;
		
		if (!repoExists){
			GithubAPI.createNewRepo(authToken, siteName);
			repoExists = true;
		}
	}
}
