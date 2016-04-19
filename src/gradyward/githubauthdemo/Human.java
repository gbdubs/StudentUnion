package gradyward.githubauthdemo;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class Human {

	@Id String id;
	List<Key<Site>> sites;
	
	private static UserService us = UserServiceFactory.getUserService();
	
	public static boolean loggedIn(){
		return us.isUserLoggedIn();
	}
	
	public static String email(){
		if (us.isUserLoggedIn()){
			User user = us.getCurrentUser();
			String email = user.getEmail();
			if (email.trim().length() > 0){
				return email;
			}
		}
		return null;
	}
	
	public static String id(){
		if (us.isUserLoggedIn()){
			User user = us.getCurrentUser();
			String id = user.getUserId();
			if (id.trim().length() > 0){
				return id;
			}
		}
		return null;
	}
	
	public static Human getOrCreate(){
		String id = Human.id();
		if (id == null){
			return null;
		}
		return getOrCreate(id);
	}
	
	public static Human getOrCreate(String id){
		Human h = ofy().load().type(Human.class).id(id).now();
		if (h == null){
			h = new Human();
			h.id = id;
			h.sites = new ArrayList<Key<Site>>();
			ofy().save().entity(h);
		}
		return h;
	}
	
	public void addSite(String siteId){
		Site s = Site.getOrCreate(siteId);
		this.sites.add(Key.create(s));
		s.owners.add(Key.create(this));
		ofy().save().entities(this, s);
	}
	
	
}
