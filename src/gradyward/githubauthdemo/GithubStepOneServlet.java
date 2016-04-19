package gradyward.githubauthdemo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GithubStepOneServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		// Set a state variable to prevent cross scripting.
		String state = UUID.randomUUID().toString();
		
		// Do something with the Secret.
		TradeSecrets.currentState = state;
		
		// Set up HTTP Redirect to Github.
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", "https://github.com/login/oauth/authorize");
		params.put("client_id", TradeSecrets.clientId);
		params.put("scope", "repo");
		params.put("state", state);
		
		// Send redirect to step one.
		resp.sendRedirect(URLTools.makeRequestURL(params));
	}
}
