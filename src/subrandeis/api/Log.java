package subrandeis.api;

import java.util.logging.Logger;

import subrandeis.util.DateUtil;

public class Log {
	
	private static final Logger theLog = Logger.getLogger("StudentUnion");

	private static String format(String format, String... strArgs){
		Object[] args = (Object[]) strArgs;
		return String.format(format, args);
	}
	
	private static String addDeets(String s){
		return s + String.format(" User = [%s] and Time = [%s].", UserAPI.email(), DateUtil.now());
	}
	
	public static void ERROR(String format, String... strArgs){
		error(addDeets(format(format, strArgs)));
	}
	
	public static void INFO(String format, String... strArgs){
		info(addDeets(format(format, strArgs)));
	}
	
	public static void WARN(String format, String... strArgs){
		warn(addDeets(format(format, strArgs)));
	}
	
	private static void error(String s){
		System.err.println(s);
		theLog.severe(s);
	}
	
	private static void warn(String s){
		System.out.println(s);
		theLog.warning(s);
	}
	
	private static void info(String s){
		System.out.println(s);
		theLog.info(s);
	}
	
}
