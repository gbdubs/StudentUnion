package subrandeis.servlet.basic;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.SecretsAPI;
import subrandeis.api.UserAPI;
import subrandeis.entities.Person;

@SuppressWarnings("serial")
public class ImageUploadServlet extends HttpServlet {

	private String absoluteURLForImageUploads = "https://raw.githubusercontent.com/"+SecretsAPI.GithubUsername+"/"+SecretsAPI.WebsiteRepository+"/gh-pages/";
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
		if (UserAPI.loggedIn()){
			Person p = Person.get(UserAPI.email());
			String encodedData = req.getParameter("imageData");
			String suffix = req.getParameter("suffix");
			if (suffix != null && encodedData != null && (p.admin || p.owner || UserAPI.isGoogleAdmin())){
				String newBlobId = UUID.randomUUID().toString();
				String path = "static/img/"+newBlobId+"."+suffix;
				String message = String.format("Image [%s] uploaded by user [%s] on [%s].", newBlobId, p.email, (new Date()).toString());
				Log.info("STARTED: " + message);
				try {
					GithubAPI.createNewFileAlreadyEncoded("website", path, message, encodedData);
				} catch (java.net.SocketTimeoutException ste){
					Log.warn("TIMEOUT: " + message);
				}
				Log.info("COMPLETE: " + message);
				String whereAt = absoluteURLForImageUploads + path;
				resp.getWriter().println(whereAt);
				return;
			}
		}
	}
	
}
