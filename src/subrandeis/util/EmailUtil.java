package subrandeis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import subrandeis.api.Log;

public class EmailUtil {

	public static List<String> cleanInputListOfEmails(String original){
		return cleanInputListOfEmails(Arrays.asList(original.split(",")));
	}
	
	public static List<String> cleanInputListOfEmails(List<String> original){
		List<String> result = new ArrayList<String>();
		for (String o : original){
			String s = toCannonicalCase(o);
			if (isAnEmail(s) && (isSchoolEmail(s) || isGoogleEmail(s))){
				result.add(s);
			} else {
				Log.WARN("EmailUtil: The email provided by the user [%s] was not valid.", o);
			}
		}
		return result;
	}
	
	
	private static final String schoolEmailSuffix = "@brandeis.edu";
	
	private static String toCannonicalCase(String s){
		return s.trim().toLowerCase();
	}
	
	private static boolean isAnEmail(String s){
		if (s.indexOf("@") == -1){
			return false;
		}
		if (s.indexOf(".", s.indexOf("@")) == -1){
			return false;
		}
		return true;
	}
	
	private static boolean isSchoolEmail(String s){
		return s.endsWith(schoolEmailSuffix);
	}
	
	private static boolean isGoogleEmail(String s){
		return s.endsWith("@gmail.com");
	}
}
