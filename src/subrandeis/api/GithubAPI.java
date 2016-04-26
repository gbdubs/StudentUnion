package subrandeis.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;

import subrandeis.entities.Page;

/**
 * Basic Github Operations, via a number of methodologies.
 * I couldn't figure out how to do many of these things without many tries, so this API serves to consolodate the
 * things that were able to get me good results.
 * For this reason, the methods are not consistent, and this should not be used as an example of how to use the
 * github libraries that are in place to do this work better than I could.
 * @author Grady
 *
 */
public class GithubAPI {
    
	  /* * * * * * * * * * * * * * * * * * * */
	 /* CONNECTION/CLIENT GITHUB UTILITIES  */
    /* * * * * * * * * * * * * * * * * * * */
	private static GitHubClient ghc;
	
	static{
		ghc = new GitHubClient();
		ghc.setCredentials(SecretsAPI.GithubUsername, SecretsAPI.GithubPassword);
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
		
		String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+newFilePath;
		
		UpdatePageRequest data = UpdatePageRequest.make(newFilePath, commitMessage, newFileBody, "gh-pages");
		
		ghc.put(apiURI, data, null);
		
	}
	
	  /* * * * * * * * * * * * * */
	 /* GET AN EXISTING PAGE SHA*/
    /* * * * * * * * * * * * * */
	public static String getFileSha(String repoName, String filePath) {
		try{
			ContentsService cs = new ContentsService(ghc);
		
			List<RepositoryContents> result = cs.getContents(RepositoryId.create(SecretsAPI.GithubUsername, repoName), filePath, "gh-pages");
			for (RepositoryContents rc : result){
				return rc.getSha();
			}
		} catch (IOException io){
			return null;
		}
		return null;
	}
	
	
	  /* * * * * * * * * * * * * * */
	 /* GET AN EXISTING PAGE TEXT */
    /* * * * * * * * * * * * * * */
	public static String getFileText(String repoName, String filePath) {
		try{
			ContentsService cs = new ContentsService(ghc);
		
			List<RepositoryContents> result = cs.getContents(RepositoryId.create(SecretsAPI.GithubUsername, repoName), filePath, "gh-pages");
			for (RepositoryContents rc : result){
				String encoded = rc.getContent();
				byte[] decoded = DatatypeConverter.parseBase64Binary(encoded);
				return new String(decoded, "UTF-8");
			}
		} catch (IOException re){
			return null;
		}
		return null;
	}
	
	
	  /* * * * * * * * */
	 /* UPDATE A PAGE */
	/* * * * * * * * */
	public static void updateFile(String repoName, String filePath, String commitMessage, String newFileBody) throws IOException {

		String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+filePath;

		UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, "gh-pages", getFileSha(repoName, filePath));

		ghc.put(apiURI, data, null);

	}
	
	public static void createOrUpdateFile(String repoName, String filePath, String commitMessage, String newFileBody) throws IOException {
		String sha = getFileSha(repoName, filePath);
		
		String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+filePath;
		
		UpdatePageRequest data;

		if (sha != null){
			data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, "gh-pages", sha);
		} else {
			data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, "gh-pages");
		}
		
		ghc.put(apiURI, data, null);
	}
	
	  /* * * * * * * * */
     /* DELETE A PAGE */
    /* * * * * * * * */
	public static void deleteFile(String repoName, String filePath, String commitMessage) throws IOException {

		String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+filePath;

		UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, "", "gh-pages", getFileSha(repoName, filePath));

		ghc.delete(apiURI, data);
	}
	
	
}
