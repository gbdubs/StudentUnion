package subrandeis.servlet.adv;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.ObjectifyAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;
import subrandeis.entities.Petition;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class PetitionServlet extends HttpServlet {
	
	static Objectify ofy = ObjectifyAPI.ofy();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{

		Petition p = Petition.get(req.getParameter("petitionId"));		
		if (p == null){
			if (req.getRequestURI().contains("petitions/new") || req.getRequestURI().contains("petition/new")){
				doRenderNewPetition(req, resp);
			} else {
				doRenderPetitionList(req, resp);
			}
		} else {
			doRenderPetition(p, req, resp);
		}
	}
	
	private void doRenderNewPetition(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
		if (!UserAPI.loggedIn()){
			resp.sendRedirect("/login?goto="+ ("/petitions/new").replace("/", "%2F"));
			return;
		}
		Person p = Person.get(UserAPI.email());
		if (!p.isBrandeisStudent()){
			resp.setContentType("text/html");
			resp.getWriter().println("You are not a Brandeis Student, so you cannot create a petition. Sorry!");
			String logoutUrl = UserAPI.logoutUrl();
			resp.getWriter().println("If you have a Brandeis account, you can login with it by first logging out.");
			resp.getWriter().println(String.format("<a href=\"%s\">Logout Here</a>", logoutUrl));
			return;
		}
		if (p.blocked){
			resp.getWriter().println("You have been blocked from creating petitions. Please contact a site owner if you believe that this is in error.");
			return;
		}
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/new-petition.jsp");
		jsp.forward(req, resp);	
		return;	
	}

	public void doRenderPetition(Petition petition, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		boolean loggedInBrandeisStudent = false;
		boolean isAdministrator = false;
		boolean isOwner = false;
		Person person = null;
		
		if (UserAPI.loggedIn()) {
			person = Person.get(UserAPI.email());
			loggedInBrandeisStudent = person.isBrandeisStudent() && !person.blocked;
			isAdministrator = person.admin || person.owner || UserAPI.isGoogleAdmin();
			isOwner = person.owner || UserAPI.isGoogleAdmin();
		}
		
		if (petition.flagged && !isAdministrator){
			doRenderPetitionList(req, resp);
			return;
		}
		if (petition.deleted && !isOwner){
			doRenderPetitionList(req, resp);
			return;
		}
		
		List<String> peopleFor = petition.getFor();
		List<String> peopleAgainst = petition.getAgainst();
		
		req.setAttribute("loggedInBrandeisStudent", loggedInBrandeisStudent);
		req.setAttribute("isAdministrator", isAdministrator);
		req.setAttribute("peopleForNum", peopleFor.size());
		req.setAttribute("peopleAgainstNum", peopleAgainst.size());
		req.setAttribute("petition", petition);
		
		resp.setContentType("text/html");
		RequestDispatcher jsp;
		if (loggedInBrandeisStudent){
			req.setAttribute("logoutUrl", UserAPI.logoutUrl("/petitions"));
			
			req.setAttribute("person", person);
			req.setAttribute("peopleFor", peopleFor);
			req.setAttribute("peopleAgainst", peopleAgainst);
			int vote = Petition.PetitionSignature.getVote(petition.petitionId, person.email);
			req.setAttribute("personFor", vote == 1);
			req.setAttribute("personAbstain", vote == 0);
			req.setAttribute("personAgainst", vote == -1);
			
			jsp = req.getRequestDispatcher("/WEB-INF/pages/petition-logged-in.jsp");
		} else {
			req.setAttribute("loginUrl", UserAPI.loginUrl(req.getRequestURI()));
			jsp = req.getRequestDispatcher("/WEB-INF/pages/petition-logged-out.jsp");
		}
		
		jsp.forward(req, resp);	
		return;	
	}
	
	public void doRenderPetitionList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		boolean canSeeDeleted = false;
		boolean canSeeFlagged = false;
		boolean loggedInBrandeisStudent = false; 
		
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			canSeeDeleted = p.owner || UserAPI.isGoogleAdmin();
			canSeeFlagged = p.admin || canSeeDeleted;
			loggedInBrandeisStudent = p.isBrandeisStudent();
		}
		
		List<Petition> allPetitions = ofy.load().type(Petition.class).order("timestamp").list();
		
		List<Petition> accepted = new ArrayList<Petition>();
		
		for (Petition p : allPetitions){
			if (p.deleted){
				if (canSeeDeleted){
					accepted.add(p);
				}
			} else if (p.flagged){
				if (canSeeFlagged){
					accepted.add(p);
				}
			} else {
				accepted.add(p);
			}
		}
		
		req.setAttribute("loginUrl", UserAPI.loginUrl(req.getRequestURI()));
		req.setAttribute("logoutUrl", UserAPI.logoutUrl());
		req.setAttribute("loggedInBrandeisStudent", loggedInBrandeisStudent);
		req.setAttribute("isAdministrator", canSeeFlagged);
		req.setAttribute("petitions", accepted);
		
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/petition-list.jsp");
		jsp.forward(req, resp);	
		return;	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String action = req.getParameter("action");
		if ("vote".equals(action)){
			doVote(req, resp);
		} else if ("new".equals(action)){
			doNewPetition(req, resp);
		} else if ("flag".equals(action)){
			doFlagging(req, resp);
		} else if ("unflag".equals(action)){
			doUnflagging(req, resp);
		} else if ("delete".equals(action)){
			doDeletion(req, resp);
		} else {
			resp.getWriter().println("The Action Parameter Was Not Well Defined.");
		}
	}
	
	public void doUnflagging(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p.owner || UserAPI.isGoogleAdmin()){
				String petitionId = req.getParameter("petitionId");
				Petition petition = Petition.get(petitionId);
				if (petition == null){
					resp.getWriter().println(String.format("Petition with ID [%s] couldn't be found.", petitionId));
					return;
				}
				petition.flagged = false;
				ofy.save().entity(petition).now();
				resp.getWriter().println("success");
				return;
			}
			resp.getWriter().println("You do not have sufficent permissions to un-flag a post.");
			return;
		}
		resp.getWriter().println("You must be logged in to un-flag a post.");
		return;
	}
	
	public void doDeletion(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p.owner || UserAPI.isGoogleAdmin()){
				String petitionId = req.getParameter("petitionId");
				Petition petition = Petition.get(petitionId);
				if (petition == null){
					resp.getWriter().println(String.format("Petition with ID [%s] couldn't be found.", petitionId));
					return;
				}
				petition.flagged = true;
				petition.deleted = true;
				ofy.save().entity(petition).now();
				resp.getWriter().println("success");
				return;
			}
			resp.getWriter().println("You do not have sufficent permissions to delete a post.");
			return;
		}
		resp.getWriter().println("You must be logged in to delete a post.");
		return;
	}
	
	public void doFlagging(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p.admin || p.owner || UserAPI.isGoogleAdmin()){
				String petitionId = req.getParameter("petitionId");
				Petition petition = Petition.get(petitionId);
				if (petition == null){
					resp.getWriter().println(String.format("Petition with ID [%s] couldn't be found.", petitionId));
					return;
				}
				petition.flagged = true;
				ofy.save().entity(petition);
				resp.getWriter().println("success");
				return;
			}
			resp.getWriter().println("You do not have sufficent permissions to flag a post.");
			return;
		}
		resp.getWriter().println("You must be logged in to flag a post.");
		return;
	}
	
	public void doNewPetition(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p.blocked){
				resp.getWriter().println("You have been banned from voting on petitions.");
				return;
			}
			if (!p.isBrandeisStudent()){
				resp.getWriter().println("Our records show that you are not a Brandeis Student. Please let our site administrators know if this is in error.");
				return;
			}
		
			String petitionName = req.getParameter("petitionName");
			String petitionBody = req.getParameter("petitionBody");
			String authorName = req.getParameter("petitionAuthorName");
			String authorEmail = p.email;
			
			Petition petition = new Petition();
			DateFormat df = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm a");
			petition.createdAt = df.format(new Date());
			petition.creatorEmail = authorEmail;
			petition.creatorName = authorName;
			petition.description = petitionBody;
			petition.timestamp = System.currentTimeMillis();
			petition.name = petitionName;
			petition.petitionId = UUID.randomUUID().toString();
			petition.flagged = false;
			petition.deleted = false;
			
			ofy.save().entity(petition).now();
			resp.sendRedirect("/petitions?petitionId="+petition.petitionId);
			return;
		}
		resp.getWriter().println("Log in with a Brandeis Email Account to create a petition");
	}
	
	public void doVote(HttpServletRequest req, HttpServletResponse resp) throws IOException{	
		
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			if (p.blocked){
				resp.getWriter().println("You have been banned from voting on petitions.");
				return;
			}
			if (!p.isBrandeisStudent()){
				resp.getWriter().println("Our records show that you are not a Brandeis Student. Please let our site administrators know if this is in error.");
				return;
			}
			String petitionId = req.getParameter("petitionId");
			Petition petition = Petition.get(petitionId);
			if (petitionId == null || petition == null){
				resp.getWriter().println("Unacceptable value for petitionId given in API call.");
				return;
			}
			
			String action = req.getParameter("forOrAgainstOrNeutral");
			if ("for".equals(action)){
				Petition.signFor(petitionId, p.email);
			} else if ("against".equals(action)){
				Petition.signAgainst(petitionId, p.email);
			} else if ("neutral".equals(action)){
				Petition.signUndo(petitionId, p.email);
			}
			resp.sendRedirect("/petition?petitionId="+petitionId);
		} else {
			resp.getWriter().println("You must log in to sign petitions.");
		}
	}
}
