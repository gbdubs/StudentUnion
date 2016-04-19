package gradyward.githubauthdemo;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class User {

	private static UserService us = UserServiceFactory.getUserService();
	
	public static boolean loggedIn(){
		return us.isUserLoggedIn();
	}
	
	public static String email(){
		if (us.isUserLoggedIn()){
			com.google.appengine.api.users.User user = us.getCurrentUser();
			String email = user.getEmail();
			if (email.trim().length() > 0){
				return email;
			}
		}
		return null;
	}
	
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
	
	public static String loginUrl(String returnTo){
		return us.createLoginURL(returnTo);
	}
	
	public static String loginUrl(){
		return us.createLoginURL("/");
	}
	
	public static String logoutUrl(){
		return us.createLoginURL("/");
	}
}
