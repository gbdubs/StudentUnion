package subrandeis.api;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * An API to interface with the Google AppEngine User API, which I believe is poorly designed.
 * I don't throw exceptions, and make basic assumptions about the state that the AppEngine API's don't make.
 * @author Grady
 *
 */
public class UserAPI {

	// A singular, static instance of the UserService to be used by the full app.
	private static UserService us = UserServiceFactory.getUserService();
	
	/**
	 * Is the User Currently Logged in?	
	 * @return Whether or not the current user is logged in.
	 */
	public static boolean loggedIn(){
		return us.isUserLoggedIn();
	}
	
	/**
	 * What is the email of the user? (Null if user is not logged in, or if the email is poorly defined)
	 * @return
	 */
	public static String email(){
		if (us.isUserLoggedIn()){
			com.google.appengine.api.users.User user = us.getCurrentUser();
			String email = user.getEmail().toLowerCase().trim();
			if (email.length() > 0){
				return email;
			}
		}
		return null;
	}
	
	/**
	 * What is the googleId associated with the user?
	 * @return The googleId (as a string)
	 */
	public static String id(){
		if (us.isUserLoggedIn()){
			com.google.appengine.api.users.User user = us.getCurrentUser();
			String id = user.getUserId();
			if (id.trim().length() > 0){
				return id;
			}
		}
		return null;
	}
	
	/**
	 * What is the user's preferred name (asssociated with their google login)?
	 * @return the user's google associated nickname
	 */
	public static String nickname(){
		if (us.isUserLoggedIn()){
			com.google.appengine.api.users.User user = us.getCurrentUser();
			String nickname = user.getNickname();
			if (nickname.trim().length() > 0){
				return nickname;
			}
		}
		return null;
	}
	
	/**
	 * Is the user the "GoogleAdmin" of the app? I.E. is the user a super-user/developer working on the project?
	 * @return whether or not the user is a developer on the project.
	 */
	public static boolean isGoogleAdmin() {
		if (loggedIn()){
			return us.isUserAdmin();
		}
		return false;
	}

	/**
	 * Creates a login URL, which will first visit the login-callback servlet, then go to the location of your choice.
	 * @param returnTo Where to go after the login and login-callback procedures (null defaults to the console)
	 * @return a url that will lead the user on these sequence of adventures
	 */
	public static String loginUrl(String returnTo){
		if (returnTo == null){
			returnTo = "/console";
		}
		return us.createLoginURL("/login-callback?goto="+returnTo);
	}
	
	/**
	 * A Logout URL, which will redirect to the console (which is blanketed with a login screen when not logged in).
	 * @return A URL to allow the user to log out.
	 */
	public static String logoutUrl(){
		return us.createLogoutURL("/console");
	}
	
	/**
	 * A Logout URL, which will redirect to the user specified page.
	 * @return A URL to allow the user to log out.
	 */
	public static String logoutUrl(String returnTo){
		return us.createLogoutURL(returnTo);
	}
}
