package subrandeis.api;

import java.util.logging.Logger;

import subrandeis.util.DateUtil;

public class Log {
	
	private static final Logger theLog = Logger.getLogger("StudentUnion");

	private static String addDeets(String s){
		return s + String.format(" User = [%s] and Time = [%s].", UserAPI.email(), DateUtil.now());
	}
	
	public static void ERROR(String s){
		error(addDeets(s));
	}
	
	public static void INFO(String s){
		info(addDeets(s));
	}
	
	public static void WARN(String s){
		warn(addDeets(s));
	}
	
	public static void error(String s){
		System.err.println(s);
		theLog.severe(s);
	}
	
	public static void warn(String s){
		System.out.println(s);
		theLog.warning(s);
	}
	
	public static void info(String s){
		System.out.println(s);
		theLog.info(s);
	}
	
	
}
