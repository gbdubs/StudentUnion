package gradyward.studentunion;

import gradyward.studentunion.utilities.Log;
import gradyward.studentunion.utilities.ObjectifyWrapper;
import gradyward.studentunion.utilities.User;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Person {
	static Objectify ofy = ObjectifyWrapper.ofy();
	
	public @Id String email; 
	
	public String nickname;
	public String googleId;
	
	@Index public boolean blocked;
	@Index public boolean candidate;
	@Index public boolean admin;
	@Index public boolean owner;

	public String imageUrl;
	public String biography;
	public String classYear;
	
	public boolean makeCandidate(){
		Person personMakingRequest = get(User.email());
		if (!personMakingRequest.owner && !User.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("MADE CANDIDATE: User [%s] made user [%s] a candidate on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.candidate = true;
		ofy.save().entity(this).now();
		return true;
	}
	
	public boolean makeNotCandidate(){
		Person personMakingRequest = get(User.email());
		if (!personMakingRequest.owner && !User.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("REMOVED CANDIDATE: User [%s] removed user [%s] as a candidate on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.candidate = false;
		ofy.save().entity(this).now();
		return true;
	}
	
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
			p.candidate = false;
			p.admin = false;
			p.owner = false;
			p.imageUrl = "/static/img/default-avatar.png";
			p.biography = "[No Biography Provided]";
			p.classYear = "20XX";
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
	
	public static Object getCandidates() {
		List<Person> candidates = ofy.load().type(Person.class).filter("candidate", true).list();
		return candidates;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getNickname(){
		return this.nickname;
	}
	
	public String getBiography(){
		return this.biography;
	}
	
	public String getImageUrl(){
		return this.imageUrl;
	}
	
	public String getClassYear(){
		return this.classYear;
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