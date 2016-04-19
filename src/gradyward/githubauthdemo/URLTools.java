package gradyward.githubauthdemo;

import java.util.Map;

public class URLTools {

	public static String makeRequestURL(Map<String, String> map){
		String url = map.get("url");
		if (url == null){
			url = "";
		}
		if (map.size() > 1){
			url = url + '?';
			for (String key : map.keySet()){
				if (!key.equals("url")){
					url += key+"="+map.get(key)+"&";
				}
			}
			url = url.substring(0, url.length()-1);
		}
		return url;
	}
	
	public static String makeRequestParams(Map<String, String> map){
		String result = "";
		for (String key : map.keySet()){
			if (!key.equals("url") && !key.equals("method")){
				result += key+"="+map.get(key)+"&";
			}
		}
		result = result.substring(0, result.length()-1);
		return result;
	}
}
