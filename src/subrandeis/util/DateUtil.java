package subrandeis.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	static DateFormat df = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' hh:mm a");
	
	public static String now(){
		return df.format(new Date());
	}
	
	public static long timestamp(){
		return (new Date()).getTime();
	}
}
