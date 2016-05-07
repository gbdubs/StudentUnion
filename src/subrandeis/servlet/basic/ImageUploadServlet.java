package subrandeis.servlet.basic;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subrandeis.api.GithubAPI;
import subrandeis.api.Log;
import subrandeis.api.SecretsAPI;
import subrandeis.api.UserAPI;

@SuppressWarnings("serial")
public class ImageUploadServlet extends HttpServlet {

	private String absoluteURLForImageUploads = String.format(
			"https://raw.githubusercontent.com/%s/%s/%s/",
			SecretsAPI.GithubUsername,
			SecretsAPI.GithubProductionRepo,
			SecretsAPI.GithubProductionBranch
	);
			
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
		if (UserAPI.isCandidate()){
			String encodedData = req.getParameter("imageData");
			String suffix = req.getParameter("suffix");
			if (suffix != null && encodedData != null){
				String newBlobId = UUID.randomUUID().toString();
				String path = "static/img/"+newBlobId+"."+suffix;
				GithubAPI.createRawEncodedFile(
						path, 
						Log.INFO("ImageUploadServlet: Image [%s] started upload.", newBlobId), 
						encodedData
				);
				String whereAt = absoluteURLForImageUploads + path;
				resp.getWriter().println(whereAt);
			} else {
				resp.getWriter().println(Log.WARN("ImageUploadServlet: Suffix and/or Encoded data undefined."));
			}
		} else {
			resp.getWriter().println(Log.WARN("ImageUploadServlet: Cannot upload an image without being logged in."));
		}
	}
	
}
