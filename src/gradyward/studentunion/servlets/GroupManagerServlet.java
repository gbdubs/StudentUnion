package gradyward.studentunion.servlets;

import gradyward.studentunion.Group;
import gradyward.studentunion.Person;
import gradyward.studentunion.utilities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GroupManagerServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		String addOrRemove = req.getParameter("addOrRemove");
		String leaderOrMember = req.getParameter("leaderOrMember");
		String groupUuid = req.getParameter("uuid");
		String emailsAsString = req.getParameter("emails");
		
		if (addOrRemove != null && leaderOrMember != null && emailsAsString != null && groupUuid != null && User.loggedIn()){
			Group g = Group.get(groupUuid);
			Person p = Person.get(User.email());
			if (g != null && p != null && (p.owner || User.isGoogleAdmin() || g.leaders.contains(p.email))){
				boolean changed = false;
				
				String[] emails = emailsAsString.split(",");
				List<String> trimmedLowerCaseEmails = new ArrayList<String>();
				for (String s : emails){
					trimmedLowerCaseEmails.add(s.toLowerCase().trim());
				}
				
				if (addOrRemove.equals("add")){
					if (leaderOrMember.equals("leader")){
						changed = g.addLeaders(trimmedLowerCaseEmails);
					} else if (leaderOrMember.equals("member")){
						changed = g.addMembers(trimmedLowerCaseEmails);
					}
				} else if (addOrRemove.equals("remove")){
					if (leaderOrMember.equals("leader")){
						changed = g.removeLeaders(trimmedLowerCaseEmails);
					} else if (leaderOrMember.equals("member")){
						changed = g.removeMembers(trimmedLowerCaseEmails);
					}
				}
				
				if (changed){
					g.updateMembershipPage(this);
				}
				
			}
			
		}
		
	}

}
