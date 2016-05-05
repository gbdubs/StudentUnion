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
	public static final String undefinedPageUrl = "/no/page/url/defined";
	
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
	 * Gets all current groups, and arranges them in alphabetical order according to their names.
	 * @return
	 */
	public static List<Group> getAllGroups(){
		List<Group> groups = ofy.load().type(Group.class).list();
		Collections.sort(groups);
		return groups;
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
		g.pageUrl = undefinedPageUrl;
		ofy.save().entity(g).now();
		Log.INFO("Created a new group with name [%s] and uuid [%s].", g.name, g.id);
		return g;
	}
	
	/**
	 * Attempts to delete the group, if one exists.
	 * @param uuid The UUID of the group to be deleted.
	 */
	public static void deleteGroup(String uuid){
		Group g = ofy.load().type(Group.class).id(uuid).now();
		if (g != null){
			ofy.delete().entity(g).now();
			Log.WARN("Deleted a group with name [%s] and uuid [%s].", g.name, g.id);
		} else {
			Log.ERROR("Attempt to delete non-existent group with uuid [%s].", uuid);
		}
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
				Log.INFO("UserAPI: Added [%s] as members to group [%s].", toAddEmails.toString(), this.name);
				return true;
			}
			return false;
		}
		Log.WARN("UserAPI: Attempt to add [%s] as members to group [%s] but was prevented.", toAddEmails.toString(), this.name);
		return false;
	}
	
	/**
	 * Removes members from the group. First checks that the user either an owner, a googleadmin or a leader of the group.
	 * @param toRemoveEmails Emails of members to be removed (duplicates or non-exists are find)
	 * @return Whether any changes were made to the group.
	 */
	public boolean removeMembers(List<String> toRemoveEmails){
		if (isLeader()){
			Set<String> emails = new HashSet<String>(this.members);
			emails.removeAll(toRemoveEmails);
			List<String> newMembers = new ArrayList<String>(emails);
			if (newMembers.size() != members.size()){
				this.members = newMembers;
				ofy.save().entity(this);
				Log.WARN("UserAPI: removed [%s] as members from group [%s].",  toRemoveEmails.toString(), this.name);
				return true;
			}
			return false;
		}
		Log.WARN(String.format("UserAPI: Attempted to remove [%s] as members of group [%s] was halted because of permissions.", toRemoveEmails.toString(), this.name));
		return false;
		
	}
	
	/**
	 * Adds leaders to the group, verifies that the requester is either an owner, a googleadmin, or a leader of the group.
	 * @param toAddEmails Emails of members to add (duplicates are fine, and ignored)
	 * @return Whether or not any changes were made.
	 */
	public boolean addLeaders(List<String> toAddEmails){
		if (isLeader()){
			Set<String> emails = new HashSet<String>(this.leaders);
			emails.addAll(toAddEmails);
			List<String> newLeaders = new ArrayList<String>(emails);
			if (newLeaders.size() != leaders.size()){
				this.leaders = newLeaders;
				ofy.save().entity(this);
				Log.INFO("User API: Added [%s] as leaders to group [%s].", newLeaders.toString(), this.name);
				return true;
			}
			return false;
		}
		Log.WARN("UserAPI: Unauthorized attempt to add [%s] as leaders to group [%s] but was prevented.", toAddEmails.toString(), this.name);
		return false;
	}
	
	/**
	 * Removes leaders from the group. First checks that the user either an owner, a googleadmin or a leader of the group.
	 * @param toRemoveEmails Emails of leaders to be removed (duplicates or non-exists are find)
	 * @return Whether any changes were made to the group.
	 */
	public boolean removeLeaders(List<String> toRemoveEmails){
		if (isLeader()){
			Set<String> emails = new HashSet<String>(this.leaders);
			emails.removeAll(toRemoveEmails);
			List<String> newLeaders = new ArrayList<String>(emails);
			if (newLeaders.size() != leaders.size()){
				this.leaders = newLeaders;
				ofy.save().entity(this);
				Log.WARN("UserAPI: removed [%s] as leaders from group [%s].",  toRemoveEmails.toString(), this.name);
				return true;
			}
			return false;
		}
		Log.WARN("UserAPI: Attempt to remove [%s] as leaders of group [%s] was prevented because of insufficent permissions.", toRemoveEmails.toString(), this.name);
		return false;
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
	
	public List<String> getAllMembers(){
		List<String> currentMembers = new ArrayList<String>(members);
		for (String leader : leaders){
			if (!currentMembers.contains(leader)){
				currentMembers.add(leader);
			}
		}
		Collections.sort(currentMembers);
		return currentMembers;
	}
}
