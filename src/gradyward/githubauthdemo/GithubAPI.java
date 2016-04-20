package gradyward.githubauthdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

public class GithubAPI {

	
	public static boolean verifyAuthTokenValid(String authToken){
		return false;
	}
	
	public static boolean createNewRepository(String authToken, String repoName){
		return false;
	}
	
	public static boolean deleteRepository(String authToken, String repoName){
		return false;
	}
	
	public static boolean changeDefaultBranchToGHPages(String authToken, String repoName){
		return false;
	}
		
	
	
	public static String createNewRepo(String authToken, String repoName){
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", "www.github.com/user/repos");
		params.put("method", "POST");
		
		params.put("access_token", authToken);
		
		params.put("name", repoName);
		params.put("description", "A new repository created on " + (new Date()).toString());
		params.put("homepage", "gradybward.com");
		params.put("private", "false");
		params.put("has_issues", "false");
		params.put("has_wiki", "false");
		params.put("has_downloads", "false");
		
		return executeRequest(params);
	}
	
	
	private static String executeRequest(Map<String, String> params){
		return excutePost(params.get("method"), params.get("url"), URLTools.makeRequestParams(params));
	}
	
	private static String excutePost(String method, String targetURL, String urlParameters) {
		HttpURLConnection connection = null;  
		
		try {
			
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			// Get Response  
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
			String line;
			
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect(); 
			}
		}
	}
	
	
	
}
