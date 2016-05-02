package subrandeis.entities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.ObjectifyAPI;
import subrandeis.api.SecretsAPI;
import subrandeis.api.UserAPI;
import subrandeis.servlet.adv.PageEditorServlet;
import subrandeis.servlet.basic.JSPRenderServlet;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnLoad;

@Entity
public class Group {

	public @Id String id; 
	public String name;
	public List<String> leaders;
	public List<String> members;
	public Map<String, String> roles;
	public String pageUrl;
	public String description;
	
	static Objectify ofy = ObjectifyAPI.ofy();
	
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
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p != null && (p.owner || UserAPI.isGoogleAdmin() || leaders.contains(p.email))){
				Set<String> emails = new HashSet<String>(this.members);
				emails.addAll(toAddEmails);
				List<String> newMembers = new ArrayList<String>(emails);
				if (newMembers.size() != members.size()){
					this.members = newMembers;
					ofy.save().entity(this);
					Log.info(String.format("UserAPI [%s] added [%s] as members to group [%s].\n", p.email, toAddEmails.toString(), this.name));
					return true;
				}
				return false;
			}
			Log.warn(String.format("UserAPI [%s] attempted to add [%s] as members to group [%s] but was prevented because they didn't have sufficent permissions.", p.email, toAddEmails.toString(), this.name));
			return false;
		}
		return false;
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
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p != null && (p.owner || UserAPI.isGoogleAdmin() || leaders.contains(p.email))){
				Set<String> emails = new HashSet<String>(this.leaders);
				emails.addAll(toAddEmails);
				List<String> newLeaders = new ArrayList<String>(emails);
				if (newLeaders.size() != leaders.size()){
					this.leaders = newLeaders;
					ofy.save().entity(this);
					Log.info(String.format("UserAPI [%s] added [%s] as leaders to group [%s].\n", p.email, newLeaders.toString(), this.name));
					return true;
				}
				return false;
			}
			Log.warn(String.format("UserAPI [%s] attempted to add [%s] as leaders to group [%s] but was prevented because they didn't have sufficent permissions.", p.email, toAddEmails.toString(), this.name));
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
	 * Updates the group membership page for a given group.
	 * Must be called from a servlet.
	 * @param caller Servlet that calls this (needed for referencing the HTML templates)
	 * @throws ServletException 
	 */
	public void updateMembershipPage(HttpServlet caller, HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			if (!this.pageUrl.equals("/no/page/url/defined")){
				if (Page.get(pageUrl) == null){
					Page.createPage(pageUrl, true);
				}
				if (Page.get(pageUrl+"/members") == null){
					Page.createPage(pageUrl + "/members", false);
				}
				
				String membershipPageUrl = this.pageUrl + "/members/index.html";
				while (membershipPageUrl.startsWith("/")){
					membershipPageUrl = membershipPageUrl.substring(1);
				}
			
				List<String> allPeople = new ArrayList<String>(leaders);
				for (String s : members){
					if (!allPeople.contains(s)){
						allPeople.add(s);
					}
				}
				
				Map<String, Person> inDB = ofy.load().type(Person.class).ids(allPeople);
				List<Person> people = new ArrayList<Person>();
				
				for (String s : allPeople){
					Person p = inDB.get(s);
					if (p == null){
						p = Person.get(s);
					}
					people.add(p);
				}
				
				Person p = Person.get(UserAPI.email());
				
				req.setAttribute("production", true);
				req.setAttribute("lastEditorEmail", p.email);
				req.setAttribute("lastEditorName", p.nickname);
				req.setAttribute("roles", roles);
				String now = (new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm a")).format(new Date());
				req.setAttribute("lastEditorDate", now);
				req.setAttribute("group", this);
				req.setAttribute("people", people);
				
				String pageHtml = JSPRenderServlet.render("/WEB-INF/pages/group-member-page.jsp", req, resp);
			
				String commitMessage = String.format("Membership page updated at %s.", (new Date()).toString());
				
				GithubAPI.updateFile(SecretsAPI.WebsiteRepository, membershipPageUrl, commitMessage, pageHtml);
				
				Log.info(String.format("Updated membership page for group [%s][%s] successfully", this.name, this.id));
			} else {
				Log.warn(String.format("No pageURL defined for group [%s][%s], so it was not updated.", this.id, this.name));
			}
		} catch (IOException ioe){
			Log.error(String.format("Error in updating membership page: [%s]", ioe.getMessage()));
		}
	}
	
	public static List<Group> getAllGroups(){
		return ofy.load().type(Group.class).list();
	}
	
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
	
}
