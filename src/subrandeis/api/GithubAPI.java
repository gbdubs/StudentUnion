package subrandeis.api;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;

import subrandeis.util.EncodingUtil;

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
			npr.content = EncodingUtil.base64Bin(content); 
			return npr;
		}
	}
	
	public static boolean createOrUpdateFile(String filePath, String commitMessage, String newFileBody) {
		Log.INFO("Github API: Attempting to create/update [%s]", filePath);
		
		try {
			String repoName = SecretsAPI.GithubProductionRepo;
		
			String sha = getFileSha(repoName, filePath);
			
			String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+filePath;
			
			sha = sha == null ? "" : sha;
			
			UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, newFileBody, SecretsAPI.GithubProductionBranch, sha);
			
			ghc.put(apiURI, data, null);
			
			Log.INFO("... Github API: Page Creation/Update Successful [%s]", filePath);
			return true;
		} catch (IOException ioe){
			Log.ERROR("... Github API: Page Creation/Update Threw Error! [%s] [%s] [%s]", filePath, commitMessage, ioe.getMessage());
			return false;
		}
	}

	public static boolean createRawEncodedFile(String newFilePath, String commitMessage, String encodedBody) {
		Log.INFO("Github API: Attempting to save new raw file for [%s]", newFilePath);
		try {
			String repoName = SecretsAPI.GithubProductionRepo;
			
			String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+newFilePath;
			
			UpdatePageRequest data = UpdatePageRequest.make(newFilePath, commitMessage, encodedBody, SecretsAPI.GithubProductionBranch, "");
			data.content = encodedBody;
			
			ghc.put(apiURI, data, null);
			
			Log.INFO("... Github API: Successfully saved new raw file for [%s]", newFilePath);
			return true;
		} catch (IOException ioe){
			Log.ERROR("... Github API: Error thrown while saving raw file for [%s] [%s]", newFilePath, ioe.getMessage());
			return false;
		}
		
	}
	
	private static String getFileSha(String repoName, String filePath) {
		Log.INFO("Github API: Attempting to get file SHA for [%s]", filePath);
		try{
			ContentsService cs = new ContentsService(ghc);
		
			List<RepositoryContents> result = cs.getContents(
					RepositoryId.create(SecretsAPI.GithubUsername, repoName), 
					filePath, 
					SecretsAPI.GithubProductionBranch
			);
			for (RepositoryContents rc : result){
				String sha = rc.getSha();
				Log.INFO("... Github API: Successfully got file SHA for [%s]", filePath);
				return sha;
			}
			Log.WARN("... Github API: Failed to find any Repository contents for file SHA for [%s]", filePath);
			return null;
		} catch (IOException ioe){
			Log.ERROR("... Github API: Error thrown while getting file SHA for [%s] [%s]", filePath, ioe.getMessage());
			return null;
		}
	}
	
	public static String getFileText(String repoName, String filePath) {
		Log.INFO("Github API: Attempting to get file text for [%s]", filePath);
		try{
			ContentsService cs = new ContentsService(ghc);
		
			List<RepositoryContents> results = cs.getContents(RepositoryId.create(SecretsAPI.GithubUsername, repoName), filePath, SecretsAPI.GithubProductionBranch);
			for (RepositoryContents rc : results){
				String encoded = rc.getContent();
				byte[] decoded = DatatypeConverter.parseBase64Binary(encoded);
				String result = new String(decoded, "UTF-8");
				Log.INFO("... Github API: Successfully got file text for [%s]", filePath);
				return result;
			}
			Log.WARN("... Github API: Failed to find any Repository contents for file text for [%s]", filePath);
			return null;
		} catch (IOException ioe){
			Log.ERROR("... Github API: Error thrown while geting file text for [%s] [%s]", filePath, ioe.getMessage());
			return null;
		}
	}
	
	public static final String deletedPageHint = "THISPAGEHASBEENDELETED893457983475983475";
	
	public static boolean deleteFile(String filePath, String commitMessage) {
		Log.INFO("Github API: Attempting to delete [%s]", filePath);
		
		try {
			String repoName = SecretsAPI.GithubProductionRepo;
			
			String apiURI = "/repos/"+SecretsAPI.GithubUsername+"/"+repoName+"/contents/"+filePath;
	
			String fileSha = getFileSha(repoName, filePath);
			
			String notFoundRedirect = "<html><body><div id=\""+deletedPageHint+"\"><script>window.location.replace('/404');</script></div></body></html>";
			
			UpdatePageRequest data = UpdatePageRequest.make(filePath, commitMessage, notFoundRedirect, SecretsAPI.GithubProductionBranch, fileSha);
			
			ghc.put(apiURI, data, null);
			Log.INFO("... Github API: Page Deletion was successful! [%s]", filePath);
			return true;
		} catch (IOException ioe){
			Log.ERROR("... Github API: Page Deletion failed with error! [%s] [%s] [%s]", filePath, commitMessage, ioe.getMessage());
			return false;
		}
	}	
}
