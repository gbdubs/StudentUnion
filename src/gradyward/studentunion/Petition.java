package gradyward.studentunion;

import gradyward.studentunion.utilities.ObjectifyWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Petition {

	static Objectify ofy = ObjectifyWrapper.ofy();
	
	@Entity
	public static class PetitionSignature{
		
		@Id public String petitionIdPlusEmail;
		@Index public String petitionId;
		// 1 corresponds to YES
		// -1 corresponds to NO
		// 0 corresponds to UNDO
		public int vote;
		public String personEmail;
		public String timestamp;
		
		private static List<String> getEmails(String petitionId, int vote){
			List<PetitionSignature> signatures = ofy.load().type(PetitionSignature.class).filter("petitionId", petitionId).list();
			List<String> emails = new ArrayList<String>();
			for (PetitionSignature ps : signatures){
				if (ps.vote == vote){
					emails.add(ps.personEmail);
				}
			}
			return emails;
		}
		
		public static void castVote(String petitionId, String personEmail, int vote){
			PetitionSignature ps = new PetitionSignature();
			ps.petitionIdPlusEmail = petitionId + "+" + personEmail;
			ps.petitionId = petitionId;
			ps.personEmail = personEmail;
			ps.vote = vote;
			ps.timestamp = (new Date()).toString();
			ofy.save().entity(ps);
		}
	}
	
	public void sign(String personEmail, int vote){
		PetitionSignature.castVote(this.petitionId, personEmail, vote);
	}
	
	public List<String> getFor(){
		return PetitionSignature.getEmails(this.petitionId, 1);
	}
	
	public List<String> getAgainst(){
		return PetitionSignature.getEmails(this.petitionId, -1);
	}
	
	@Id public String petitionId;
	@Index public long timestamp;
	public String name;
	public String description;
	public String creatorEmail;
	public String createdAt;
	
	@Index public boolean flagged;
	public boolean deleted;

}
