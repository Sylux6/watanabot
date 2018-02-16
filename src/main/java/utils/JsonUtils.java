package utils;

import org.json.JSONObject;

public class JsonUtils {
    
    static public JSONObject stringToJson(String s) {
	return new JSONObject(s);
    }

}
