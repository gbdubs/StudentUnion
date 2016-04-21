package gradyward.deprecated;

import gradyward.studentunion.Secrets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GithubStepTwoServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Get the state and code (as claimed by GitHub)
		String claimedState = req.getParameter("state"); 
		String code = req.getParameter("code");
		
		// Verify that the state matches the other token
		if (!claimedState.equals(Secrets.currentState)){
			throw new RuntimeException("IMPROPPER ACCESS! STATE VARIABLES DID NOT MATCH.");
		}

		// Set up HTTP Request to Github Step 2
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", "https://github.com/login/oauth/access_token");
		params.put("client_id", Secrets.clientId);
		params.put("client_secret", Secrets.clientSecret);
		params.put("code", code);
		
		// Run the HTTP Request 
		String result = executePost(params);
		
		// Find the token, if it exists
		String token = extractTokenFromResponse(result);
		
		if (token != null){
			
			// Do something with the token
			Secrets.currentToken = token;
			
			// Redirect to a new page
			resp.sendRedirect("/github-auth-success");
			
		} else {
			
			// Redirect to a failure page
			resp.sendRedirect("/github-auth-failure");
			
		}
	}
	
	private static String extractTokenFromResponse(String response){
		if (response != null){
			int start = response.indexOf("access_token=") + 13;
			if (start == -1){
				return null;
			}
			int end = response.indexOf("&", start);
			if (end == -1){
				return null;
			}
			String token = response.substring(start, end);
			return token;
		}
		return null;
	}

	private static String executePost(Map<String, String> params){
		return excutePost(params.get("url"), URLTools.makeRequestParams(params));
	}
	
	private static String excutePost(String targetURL, String urlParameters) {
		HttpURLConnection connection = null;  
		
		try {
			
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
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
