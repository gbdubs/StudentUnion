package subrandeis.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;

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
		
		public static UpdatePageRequest make(String path, String message, String content, String branch, String sha){
			UpdatePageRequest npr = new UpdatePageRequest();
			npr.path = path; npr.message = message; npr.branch = branch; npr.sha = sha;
			npr.content =  DatatypeConverter.printBase64Binary (content.getBytes(StandardCharsets.UTF_8)); 
			return npr;
		}
	}
	
	public static void createRawEncodedFile(String newFilePath, String commitMessage, String encodedBody) {
		try {
			String repoName = SecretsAPI.GithubProductionRepo;
			
			String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+newFilePath;
			
			UpdatePageRequest data = UpdatePageRequest.make(newFilePath, commitMessage, encodedBody, SecretsAPI.GithubProductionBranch, "");
			data.content = encodedBody;
			
			ghc.put(apiURI, data, null);
		} catch (IOException ioe){
			
		}
		
	}
	
	  /* * * * * * * * * * * * * */
	 /* GET AN EXISTING PAGE SHA*/
    /* * * * * * * * * * * * * */
	public static String getFileSha(String repoName, String filePath) {
		try{
			ContentsService cs = new ContentsService(ghc);
		
			List<RepositoryContents> result = cs.getContents(
					RepositoryId.create(SecretsAPI.GithubUsername, repoName), 
					filePath, 
					SecretsAPI.GithubProductionBranch
			);
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
		
			List<RepositoryContents> result = cs.getContents(RepositoryId.create(SecretsAPI.GithubUsername, repoName), filePath, SecretsAPI.GithubProductionBranch);
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
	
	public static void createOrUpdateFile(String filePath, String commitMessage, String newFileBody) {
		try {
			String repoName = SecretsAPI.GithubProductionRepo;
		
			String sha = getFileSha(repoName, filePath);
			
			String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+filePath;
			
			sha = sha == null ? "" : sha;
			
			UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, SecretsAPI.GithubProductionBranch, sha);
			
			ghc.put(apiURI, data, null);
		} catch (IOException ioe){
			
		}
	}
	
	public static final String deletedPageHint = "THISPAGEHASBEENDELETED893457983475983475";
	
	  /* * * * * * * * */
     /* DELETE A PAGE */
    /* * * * * * * * */
	public static boolean deleteFile(String filePath, String commitMessage) {
		Log.info(String.format("Github API: Attempting to delete [%s] (%s)", filePath, commitMessage));
		try {
			String repoName = SecretsAPI.GithubProductionRepo;
			
			String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+filePath;
	
			String fileSha = getFileSha(repoName, filePath);
			
			String notFoundRedirect = "<html><body><div id=\""+deletedPageHint+"\"><script>window.location.replace('/404');</script></div></body></html>";
			
			UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, notFoundRedirect, SecretsAPI.GithubProductionBranch, fileSha);
			
			ghc.put(apiURI, data, null);
			Log.info(String.format("... Github API Page Deletion was successful! [%s] (%s)", filePath, commitMessage));
			return true;
		} catch (IOException ioe){
			Log.warn(String.format("... Github API Page Deletion failed with error! [%s] [%s] [%s]", filePath, commitMessage, ioe.getMessage()));
			return false;
		}
	}	
}
