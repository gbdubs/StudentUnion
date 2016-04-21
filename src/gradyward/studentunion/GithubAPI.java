package gradyward.studentunion;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.ContentsService;

public class GithubAPI {
    
	  /* * * * * * * * * * * * * * * * * * * */
	 /* CONNECTION/CLIENT GITHUB UTILITIES  */
    /* * * * * * * * * * * * * * * * * * * */
	private static GitHubClient githubClient;
	
	private static GitHubClient ghc(){
		if (githubClient == null){
			githubClient = new GitHubClient();
			authenticateClient(githubClient);
		}
		return githubClient;
	}
	
	private static void authenticateClient(GitHubClient gc){
		gc.setCredentials(Secrets.GithubUsername, Secrets.GithubPassword);
	}

	  /* * * * * * * * * * */
	 /* CREATE A NEW PAGE */
    /* * * * * * * * * * */
	public static class UpdatePageRequest {

		String path; String message; String content; String branch; String sha;
		
		public static UpdatePageRequest make(String path, String message, String content, String branch){
			return make(path, message, content, branch, "");
		}
		
		public static UpdatePageRequest make(String path, String message, String content, String branch, String sha){
			UpdatePageRequest npr = new UpdatePageRequest();
			npr.path = path; npr.message = message; npr.branch = branch; npr.sha = sha;
			npr.content =  DatatypeConverter.printBase64Binary (content.getBytes(StandardCharsets.UTF_8)); 
			return npr;
		}
	}
	
	public static void createNewFile(String repoName, String newFilePath, String commitMessage, String newFileBody) throws IOException {
		
		String apiURI = "/repos/"+Secrets.GithubUsername+"/"+repoName+"/contents/"+newFilePath;
		
		UpdatePageRequest data = UpdatePageRequest.make(newFilePath, commitMessage, newFileBody, "gh-pages");
		
		ghc().put(apiURI, data, null);
		
	}
	
	  /* * * * * * * * * * * * * */
	 /* GET AN EXISTING PAGE SHA*/
    /* * * * * * * * * * * * * */
	public static String getFileSha(String repoName, String filePath) throws IOException {
		try{
			ContentsService cs = new ContentsService(ghc());
		
			List<RepositoryContents> result = cs.getContents(RepositoryId.create(Secrets.GithubUsername, repoName), filePath, "gh-pages");
			for (RepositoryContents rc : result){
				return rc.getSha();
			}
		} catch (RequestException re){
			return null;
		}
		return null;
	}
	
	
	  /* * * * * * * * * * * * * * */
	 /* GET AN EXISTING PAGE TEXT */
    /* * * * * * * * * * * * * * */
	public static String getFileText(String repoName, String filePath) throws IOException {
		try{
			ContentsService cs = new ContentsService(ghc());
		
			List<RepositoryContents> result = cs.getContents(RepositoryId.create(Secrets.GithubUsername, repoName), filePath, "gh-pages");
			for (RepositoryContents rc : result){
				return rc.getContent();
			}
		} catch (RequestException re){
			return null;
		}
		return null;
	}
	
	  /* * * * * * * * */
	 /* UPDATE A PAGE */
	/* * * * * * * * */
	public static void updateFile(String repoName, String filePath, String commitMessage, String newFileBody) throws IOException {

		String apiURI = "/repos/"+Secrets.GithubUsername+"/"+repoName+"/contents/"+filePath;

		UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, "gh-pages", getFileSha(repoName, filePath));

		ghc().put(apiURI, data, null);

	}
	
	public static void createOrUpdateFile(String repoName, String filePath, String commitMessage, String newFileBody) throws IOException {
		String sha = getFileSha(repoName, filePath);
		
		String apiURI = "/repos/"+Secrets.GithubUsername+"/"+repoName+"/contents/"+filePath;
		
		UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, "gh-pages");

		if (sha != null){
			data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, "gh-pages", sha);
		}
		
		ghc().put(apiURI, data, null);
	}
	
	
}
