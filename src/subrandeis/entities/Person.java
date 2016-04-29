package subrandeis.entities;

import java.util.Date;
import java.util.List;

import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Person {
	static Objectify ofy = ObjectifyAPI.ofy();
	
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
	
	public boolean fromDatabase;
	
	/**
	 * Makes the Person a candidate (DOES SAVE)
	 * @return whether or not save was done.
	 */
	public boolean makeCandidate(){
		Person personMakingRequest = get(UserAPI.email());
		if (!personMakingRequest.owner && !UserAPI.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("MADE CANDIDATE: UserAPI [%s] made user [%s] a candidate on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.candidate = true;
		ofy.save().entity(this).now();
		return true;
	}
	
	/**
	 * Makes the Person NOT a candidate (DOES SAVE)
	 * @return whether or not save was done.
	 */
	public boolean makeNotCandidate(){
		Person personMakingRequest = get(UserAPI.email());
		if (!personMakingRequest.owner && !UserAPI.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("REMOVED CANDIDATE: UserAPI [%s] removed user [%s] as a candidate on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.candidate = false;
		ofy.save().entity(this).now();
		return true;
	}
	
	/**
	 * Makes the Person an administrator (DOES SAVE)
	 * @return whether or not save was done.
	 */
	public boolean makeAdmin(){
		Person personMakingRequest = get(UserAPI.email());
		if (!personMakingRequest.owner && !UserAPI.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("MADE ADMIN: UserAPI [%s] made user [%s] an admin on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = true;
		ofy.save().entity(this).now();
		return true;
	}
	
	/**
	 * Makes the Person NOT an administrator (DOES SAVE) (also disqualifies them from ownership)
	 * @return whether or not save was done.
	 */
	public boolean makeNotAdmin(){
		Person personMakingRequest = get(UserAPI.email());
		if (!personMakingRequest.owner && !UserAPI.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("REMOVED ADMIN: UserAPI [%s] removed user [%s] as an admin on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = false;
		this.owner = false;
		ofy.save().entity(this).now();
		return true;
	}
	
	/**
	 * Makes the Person an Owner (DOES SAVE)
	 * @return whether or not save was done.
	 */
	public boolean makeOwner(){
		Person personMakingRequest = get(UserAPI.email());
		if (!personMakingRequest.owner && !UserAPI.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("MADE OWNER: UserAPI [%s] made user [%s] an owner on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.admin = true;
		this.owner = true;
		ofy.save().entity(this).now();
		return true;
	}

	/**
	 * Makes the Person NOT an Owner (DOES SAVE)
	 * @return whether or not save was done.
	 */
	public boolean makeNotOwner(){
		Person personMakingRequest = get(UserAPI.email());
		if (!personMakingRequest.owner && !UserAPI.isGoogleAdmin()){
			return false;
		}
		Log.info(String.format("REMOVED OWNER: UserAPI [%s] removed user [%s] as an owner on [%s]\n", personMakingRequest.email, email, (new Date()).toString()));
		this.owner = false;
		ofy.save().entity(this).now();
		return true;
	}
	
	/**
	 * Gets the person described by their email. Creates a new person if nobody is known with that email (DOES NOT SAVE in this case)
	 * @return whether or not save was done.
	 */
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
			p.fromDatabase = false;
		} else {
			p.fromDatabase = true;
		}
		return p;
	}
	
	/**
	 * Gets all site owners
	 * @return all site owners
	 */
	public static List<Person> getOwners(){
		List<Person> owners = ofy.load().type(Person.class).filter("owner", true).list();
		return owners;
	}
	
	/**
	 * Gets all site administrators
	 * @return all site administrators
	 */
	public static List<Person> getAdmins(){
		List<Person> owners = ofy.load().type(Person.class).filter("admin", true).list();
		return owners;
	}
	
	/**
	 * Gets all candidates
	 * @return all candidates
	 */
	public static List<Person> getCandidates() {
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
	
	/**
	 * Compares a person to an object - people are considered the same as their email addresses
	 * @return equality based on email address
	 */
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
	
	/**
	 * Decideds whether or not a person is a Brandeis Student.
	 * Currently this is only determined by whether or not they have a Brandeis.edu Email address.
	 * We will try to come up with a more sophisticated way of doing this later.
	 * However, since votes are public, we assume that outsiders will be deterred from voting unless
	 * they are current students
	 * @return Whether or not the person is a Brandeis Student
	 */
	public boolean isBrandeisStudent(){
		return email.contains("@brandeis.edu") || UserAPI.isGoogleAdmin();
	}
	
}