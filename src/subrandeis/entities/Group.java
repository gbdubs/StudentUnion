package subrandeis.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.servlet.adv.PageEditorServlet;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnLoad;

@Entity
public class Group implements Comparable<Group>{

	// Randomly assigned UUID
	public @Id String id;
	
	public String name;
	public List<String> leaders;
	public List<String> members;
	public String description;
	
	// Map from Email to User Defined Role
	public Map<String, String> roles;
	
	// Page Base URL, Memberpage URL will be /pageUrl/members
	public String pageUrl;
	
	public static Objectify ofy = ObjectifyAPI.ofy();
	
	/*
	 * A test on the load of the Group which  
	 */
	@OnLoad void checkForNulls() { 
		if (leaders == null){
			leaders = new ArrayList<String>();
		}
		if (members == null){
			members = new ArrayList<String>();
		}
		if (roles == null){
			roles = new HashMap<String, String>();
		}
	}
	
	/**
	 * Gets a group from the datastore, and returns null if none is found with given UUID.
	 * @param uuid Group Unique Identifier (not the name)
	 * @return A group, or null, if none is found.
	 */
	public static Group get(String uuid){
		if (uuid == null){
			return null;
		}
		return ofy.load().type(Group.class).id(uuid).now();
	}
	
	/**
	 * Creates a new group with a given name. Assigns it a UUID. (DOES NOT SAVE)
	 * @param name The name of the new group
	 * @return A new group entity.
	 */
	public static Group createNewGroup(String name){
		Group g = new Group();
		g.name = name;
		g.id = UUID.randomUUID().toString();
		g.leaders = new ArrayList<String>();
		g.members = new ArrayList<String>();
		g.pageUrl = "/no/page/url/defined";
		return g;
	}
	
	/**
	 * Attempts to delete the group, if one exists.
	 * @param uuid The UUID of the group to be deleted.
	 */
	public static void deleteGroup(String uuid){
		ofy.delete().type(Group.class).id(uuid).now();
	}
	
	/**
	 * Adds Members to the group, verifies that the requester is either an owner, a googleadmin, or a leader of the group.
	 * @param toAddEmails Emails of members to add (duplicates are fine, and ignored)
	 * @return Whether or not any changes were made.
	 */
	public boolean addMembers(List<String> toAddEmails){
		if (isLeader()){
			Set<String> emails = new HashSet<String>(this.members);
			emails.addAll(toAddEmails);
			List<String> newMembers = new ArrayList<String>(emails);
			if (newMembers.size() != members.size()){
				this.members = newMembers;
				ofy.save().entity(this);
				Log.INFO(String.format("UserAPI: Added [%s] as members to group [%s].\n", toAddEmails.toString(), this.name));
				return true;
			}
			return false;
		}
		Log.WARN(String.format("UserAPI: Attempt to add [%s] as members to group [%s] but was prevented.", UserAPI.email(), toAddEmails.toString(), this.name));
	}
	
	/**
	 * Removes members from the group. First checks that the user either an owner, a googleadmin or a leader of the group.
	 * @param toRemoveEmails Emails of members to be removed (duplicates or non-exists are find)
	 * @return Whether any changes were made to the group.
	 */
	public boolean removeMembers(List<String> toRemoveEmails){
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p != null && (p.owner || UserAPI.isGoogleAdmin() || leaders.contains(p.email))){
				Set<String> emails = new HashSet<String>(this.members);
				emails.removeAll(toRemoveEmails);
				List<String> newMembers = new ArrayList<String>(emails);
				if (newMembers.size() != members.size()){
					this.members = newMembers;
					ofy.save().entity(this);
					Log.info(String.format("UserAPI [%s] removed [%s] as members from group [%s].\n", p.email, toRemoveEmails.toString(), this.name));
					return true;
				}
				return false;
			}
			Log.warn(String.format("UserAPI [%s] attempted to remove [%s] as members of group [%s] but was prevented because they didn't have sufficent permissions.", p.email, toRemoveEmails.toString(), this.name));
			return false;
		}
		return false;
	}
	
	/**
	 * Adds leaders to the group, verifies that the requester is either an owner, a googleadmin, or a leader of the group.
	 * @param toAddEmails Emails of members to add (duplicates are fine, and ignored)
	 * @return Whether or not any changes were made.
	 */
	public boolean addLeaders(List<String> toAddEmails){
		
			Person p = Person.get(UserAPI.email());
			if (p != null && (p.owner || UserAPI.isGoogleAdmin() || leaders.contains(p.email))){
				Set<String> emails = new HashSet<String>(this.leaders);
				emails.addAll(toAddEmails);
				List<String> newLeaders = new ArrayList<String>(emails);
				if (newLeaders.size() != leaders.size()){
					this.leaders = newLeaders;
					ofy.save().entity(this);
					Log.INFO(String.format("User API: Added [%s] as leaders to group [%s].", newLeaders.toString(), this.name));
					return true;
				}
				return false;
			}
			Log.WARN(String.format("UserAPI: Unauthorized attempt to add [%s] as leaders to group [%s] but was prevented.", toAddEmails.toString(), this.name));
			return false;
		}
		return false;
	}
	
	/**
	 * Removes leaders from the group. First checks that the user either an owner, a googleadmin or a leader of the group.
	 * @param toRemoveEmails Emails of leaders to be removed (duplicates or non-exists are find)
	 * @return Whether any changes were made to the group.
	 */
	public boolean removeLeaders(List<String> toRemoveEmails){
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p != null && (p.owner || UserAPI.isGoogleAdmin() || leaders.contains(p.email))){
				Set<String> emails = new HashSet<String>(this.leaders);
				emails.removeAll(toRemoveEmails);
				List<String> newLeaders = new ArrayList<String>(emails);
				if (newLeaders.size() != leaders.size()){
					this.leaders = newLeaders;
					ofy.save().entity(this);
					Log.info(String.format("UserAPI [%s] removed [%s] as leaders from group [%s].\n", p.email, toRemoveEmails.toString(), this.name));
					return true;
				}
				return false;
			}
			Log.warn(String.format("UserAPI [%s] attempted to remove [%s] as leaders of group [%s] but was prevented because they didn't have sufficent permissions.", p.email, toRemoveEmails.toString(), this.name));
			return false;
		}
		return false;
	}
	
	/**
	 * Gets all current groups, and arranges them in alphabetical order according to their names.
	 * @return
	 */
	public static List<Group> getAllGroups(){
		List<Group> groups = ofy.load().type(Group.class).list();
		Collections.sort(groups);
		return groups;
	}
	
	// Getters, need to exist for proper JSP rendering.
	
	public String getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public List<String> getLeaders(){
		return this.leaders;
	}
	
	public List<String> getMembers(){
		return this.members;
	}
	
	public String getPageUrl(){
		return this.pageUrl;
	}
	
	public Map<String, String> getRoles(){
		return this.roles;
	}

	// Compare two groups based on their name, lexicographically.
	@Override
	public int compareTo(Group other) {
		return name.compareTo(other.name);
	}
	
	public boolean isLeader(){
		return isLeader(Person.get());
	}
	
	public boolean isLeader(Person p){
		if (p != null){
			if (leaders.contains(p.email)){
				return true;
			}
			if (UserAPI.isOwner(p)){
				return true;
			}
		}
		return false;
	}
}
