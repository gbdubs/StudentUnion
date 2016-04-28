package subrandeis.servlet.adv;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;

@SuppressWarnings("serial")
public class ImageUploadServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			String encodedData = req.getParameter("imageData");
			String suffix = req.getParameter("suffix");
			if (suffix != null && encodedData != null && (p.admin || p.owner || UserAPI.isGoogleAdmin())){
				String newBlobId = UUID.randomUUID().toString();
				String path = "static/img/"+newBlobId+"."+suffix;
				String message = String.format("Image [%s] uploaded by user [%s] on [%s]", newBlobId, p.email, (new Date()).toString());
				System.out.println("Started Upload...");
				try {
					GithubAPI.createNewFileAlreadyEncoded("website", path, message, encodedData);
				} catch (java.net.SocketTimeoutException ste){
					
				}
				System.out.println("Upload Complete!");
				String whereAt = "https://raw.githubusercontent.com/subrandeis/website/gh-pages/" + path;
				resp.getWriter().println(whereAt);
				return;
			}
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException { 
		// Finishes up, sends to the console page.
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/pages/image-upload.jsp");
		jsp.forward(req, resp);	
		return;
	}
	
}
