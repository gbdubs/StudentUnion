package subrandeis.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import subrandeis.api.Log;

public class EncodingUtil {
	
	public static String percent(String original){
		try {
			return URLEncoder.encode(original, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.ERROR("EncodingUtil: Percent EncodingUtil Failed with exception on the string [%s], the message was [%s]", original, e.getMessage());
			return null;
		}
	}

	public static String base64Bin(String original) {
		return DatatypeConverter.printBase64Binary (original.getBytes(StandardCharsets.UTF_8));
	}
}
