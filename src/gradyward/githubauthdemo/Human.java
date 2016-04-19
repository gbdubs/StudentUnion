package gradyward.githubauthdemo;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class Human {
	static Objectify ofy = ObjectifyWrapper.ofy();
	
	@Id String id;
	List<Key<Site>> sites;

	
	public static Human getOrCreate(){
		String id = User.id();
		if (id == null){
			return null;
		}
		return getOrCreate(id);
	}
	
	public static Human getOrCreate(String id){
		Human h = ofy.load().type(Human.class).id(id).now();
		if (h == null){
			h = new Human();
			h.id = id;
			h.sites = new ArrayList<Key<Site>>();
			ofy.save().entity(h);
		}
		return h;
	}
	
	public void addSite(String siteId){
		Site s = Site.getOrCreate(siteId);
		this.sites.add(Key.create(s));
		s.owners.add(Key.create(this));
		ofy.save().entities(this, s);
	}
	
	public List<Site> getSites(){
		if (sites != null && sites.size() > 0){
			return new ArrayList<Site>(ofy.load().keys(sites).values());
		}
		return new ArrayList<Site>();
	}
}
