package subrandeis.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Story implements Comparable<Story> {

	static Objectify ofy = ObjectifyAPI.ofy();
	
	@Id public String storyId;
	@Index public long timestamp;
	public String title;
	public boolean edited;
	public String postedAt;
	public String editedAt;
	public String authorName;
	public String authorEmail;
	public String lastEditorName;
	public String lastEditorEmail;
	public String body;
	
	public static Story get(String sid) {
		if (sid == null){
			return null;
		}
		return ofy.load().type(Story.class).id(sid).now();
	}
	
	public static void create(String title, String body){
		Story s = new Story();
		s.storyId = UUID.randomUUID().toString();
		s.title = title;
		s.body = body;
		s.edited = false;
		
		Date d = new Date();
		s.timestamp = (d).getTime();
		DateFormat df = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm a");
		s.postedAt = df.format(d);
		s.editedAt = s.postedAt;
		
		Person p = Person.get(UserAPI.email());
		s.authorName = p.nickname;
		s.authorEmail = p.email;
		
		s.lastEditorName = s.authorName;
		s.lastEditorEmail = s.authorEmail;
		
		ofy.save().entity(s).now();
	}
	
	public void update(String title, String body) {
		this.title = title;
		this.body = body;
		this.edited = false;
		
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm a");
		this.editedAt = df.format(d);
		
		Person p = Person.get(UserAPI.email());
		this.lastEditorName = p.nickname;
		this.lastEditorEmail = p.email;
		
		ofy.save().entity(this).now();
	}
	
	public static void delete(String storyId){
		ofy.delete().type(Story.class).id(storyId).now();
	}
	
	@Override
	public int compareTo(Story that) {
		return Long.compare(that.timestamp, this.timestamp);
	}
	
	public static List<Story> getAll(){
		List<Story> stories = ofy.load().type(Story.class).limit(10000).list();
		Collections.sort(stories);
		return stories;
	}
	
	public String getStoryId(){
		return storyId;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean getEdited(){
		return edited;
	}
	
	public String getPostedAt(){
		return postedAt;
	}
	
	public String getEditedAt(){
		return editedAt;
	}
	
	public String getAuthorName(){
		return authorName;
	}
	
	public String getAuthorEmail(){
		return authorEmail;
	}
	
	public String getLastEditorName(){
		return lastEditorName;
	}
	
	public String getLastEditorEmail(){
		return lastEditorEmail;
	}
	
	public String getBody(){
		return body;
	}

}