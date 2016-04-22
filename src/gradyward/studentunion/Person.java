package gradyward.studentunion;

import gradyward.studentunion.utilities.Log;
import gradyward.studentunion.utilities.ObjectifyWrapper;
import gradyward.studentunion.utilities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Person {
	static Objectify ofy = ObjectifyWrapper.ofy();
	
	@Id String email; 
	
	String nickname;
	String googleId;
	
	@Index boolean blocked;
	@Index boolean admin;
	@Index boolean owner;
	
	List<String> permissions;
	
	public boolean makeAdmin(){
		Person personMakingRequest = get(User.email());
		if (!personMakingRequest.owner && !User.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("MADE ADMIN: User [%s] made user [%s] an admin on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = true;
		ofy.save().entity(this).now();
		return true;
	}
	
	public boolean makeNotAdmin(){
		Person personMakingRequest = get(User.email());
		if (!personMakingRequest.owner && !User.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("REMOVED ADMIN: User [%s] removed user [%s] as an admin on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = false;
		this.owner = false;
		ofy.save().entity(this).now();
		return true;
	}
	
	public boolean makeOwner(){
		Person personMakingRequest = get(User.email());
		if (!personMakingRequest.owner && !User.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("MADE OWNER: User [%s] made user [%s] an owner on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = true;
		this.owner = true;
		ofy.save().entity(this).now();
		return true;
	}
	
	public boolean makeNotOwner(){
		Person personMakingRequest = get(User.email());
		if (!personMakingRequest.owner && !User.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("REMOVED OWNER: User [%s] removed user [%s] as an owner on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = true;
		this.owner = false;
		ofy.save().entity(this).now();
		return true;
	}
	
	public static Person get(String email){
		if (email == null){
			return null;
		}
		Person p = ofy.load().type(Person.class).id(email).now();
		if (p == null){
			p = new Person();
			p.email = email;
			p.googleId = null;
			p.nickname = "[No Nickname]";
			p.blocked = false;
			p.admin = false;
			p.owner = false;
			p.permissions = new ArrayList<String>();
		}
		return p;
	}
	
	public static List<Person> getOwners(){
		List<Person> owners = ofy.load().type(Person.class).filter("owner", true).list();
		return owners;
	}
	
	public static List<Person> getAdmins(){
		List<Person> owners = ofy.load().type(Person.class).filter("admin", true).list();
		return owners;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getNickname(){
		return this.nickname;
	}
	
	public boolean equals(Object o){
		
		if (o instanceof String){
			String s = (String) o;
			return s.equals(email);
		}

		if (o instanceof Person){
			return email.equals(((Person) o).email);
		}
		
		return false;
	}
	
}