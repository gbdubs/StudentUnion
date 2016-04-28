package subrandeis.api;

import java.util.logging.Logger;

public class Log {
	
	private static final Logger theLog = Logger.getLogger("StudentUnion");

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
