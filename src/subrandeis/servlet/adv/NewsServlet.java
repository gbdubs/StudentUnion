package subrandeis.servlet.adv;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.SecretsAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Story;
import subrandeis.servlet.basic.JSPRenderServlet;

@SuppressWarnings("serial")
public class NewsServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		if ("edit".equals(req.getParameter("action")) && UserAPI.isAdmin()){
			
			Story story = Story.get(req.getParameter("storyId"));
			if (story != null){
				req.setAttribute("story", story);
			}
			resp.setContentType("text/html");
			RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/news-editor.jsp");
			jsp.forward(req, resp);
			
		} else {
			
			req.setAttribute("isAdmin", UserAPI.isAdmin());
			req.setAttribute("stories", Story.getAll());
			req.setAttribute("production", false);
			
			resp.setContentType("text/html");
			RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/news.jsp");
			jsp.forward(req, resp);
			
		}

	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if (!UserAPI.isAdmin()){
			resp.getWriter().println("Log in with a valid account to make changes to the news.");
			return;
		}
		String action = req.getParameter("action");
		if ("create".equals(action)){
			doNewNewsStory(req, resp);
		} else if ("update".equals(action)){
			doUpdateNewsStory(req, resp);
		} else if ("delete".equals(action)){
			doDeleteNewsStory(req, resp);
		}
	}
	
	public void doDeleteNewsStory(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String storyId = req.getParameter("storyId");
		
		Story.delete(storyId);
		
		String message = String.format(
				"[Story [%s] deleted by user [%s] on [%s].",
				storyId,
				UserAPI.email(),
				(new Date()).toString()
		);
		
		Log.info(message);
		
		doUpdateNewsPage(req, resp);
		
		resp.sendRedirect("/news");
		
	}
	
	public void doUpdateNewsStory(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String storyId = req.getParameter("storyId");
		String title = req.getParameter("storyTitle");
		String body = req.getParameter("storyBody");
	
		Story story = Story.get(storyId);
		if (title == null || body == null || story == null){
			resp.getWriter().println("Check your API call (New News Story, NewsServlet). Story MIGHT not exist.");
			return;
		} 
		
		story.update(title, body);
		
		String message = String.format(
				"[Story [%s] updated by user [%s] on [%s].",
				story.storyId,
				UserAPI.email(),
				(new Date()).toString()
		);
		
		doUpdateNewsPage(req, resp);
		
		Log.info(message);
		
		resp.sendRedirect("/news");
		
	}
	
	public void doNewNewsStory(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String title = req.getParameter("storyTitle");
		String body = req.getParameter("storyBody");
		
		if (title == null || body == null){
			resp.getWriter().println("Check your API call (New News Story, NewsServlet)");
			return;
		} 
		
		Story.create(title, body);
		
		doUpdateNewsPage(req, resp);
		
		resp.sendRedirect("/news");
		
	}
	
	public void doUpdateNewsPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		List<Story> stories = Story.getAll();
		
		req.setAttribute("production", true);
		req.setAttribute("stories", stories);
		
		String content = JSPRenderServlet.render("/WEB-INF/pages/news.jsp", req, resp);
		
		String message = String.format(
				"News auto generated by [%s] on [%s].", 
				UserAPI.email(), 
				(new Date()).toString()
		);
		
		GithubAPI.createOrUpdateFile("news/index.html", message, content);
		
		Log.info(message);
		
	}

}