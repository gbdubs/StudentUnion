package subrandeis.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import subrandeis.api.Log;

public class Encoding {
	
	public static String percent(String s){
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.ERROR("Percent Encoding Failed with exception on the string [%s], the message was [%s]", s, e.getMessage());
			return null;
		}
	}
}
