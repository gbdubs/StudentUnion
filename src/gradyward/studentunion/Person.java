package gradyward.studentunion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Person {
	static Objectify ofy = ObjectifyWrapper.ofy();
	
	@Id String email; 
	
	String googleId;
	
	boolean blocked;
	boolean admin;
	boolean owner;
	
	List<String> permissions;
	
	
	
	public boolean makeAdmin(){
		Person personMakingRequest = get(User.id());
		if (!personMakingRequest.owner){
			return false;
		}
		Log.info(String.format("MADE ADMIN: User [%s] made user [%s] an admin on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = true;
		ofy.save().entity(this);
		return true;
	}
	
	public boolean makeOwner(){
		Person personMakingRequest = get(User.id());
		if (!personMakingRequest.owner){
			return false;
		}
		Log.info(String.format("MADE OWNER: User [%s] made user [%s] an owner on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = true;
		this.owner = true;
		ofy.save().entity(this);
		return true;
	}
	
	public static Person get(String email){
		Person p = ofy.load().type(Person.class).id(email).now();
		if (p == null){
			p = new Person();
			p.email = email;
			p.googleId = null;
			p.blocked = false;
			p.admin = false;
			p.owner = false;
			p.permissions = new ArrayList<String>();
		}
		return p;
	}
	
}