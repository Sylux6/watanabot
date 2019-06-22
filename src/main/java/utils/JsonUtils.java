package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    
    static public JSONObject stringToJsonObject(String s) {
        return new JSONObject(s);
    }
    
    static public JSONArray stringToJsonArray(String s) {
	    return new JSONArray(s);
    }

    static public String getStringOrNull(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            return "";
        }
    }

}
