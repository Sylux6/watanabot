package utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JsonUtils {

    fun stringToJsonObject(s: String): JSONObject {
        return JSONObject(s)
    }

    fun stringToJsonArray(s: String): JSONArray {
        return JSONArray(s)
    }

    fun getStringOrNull(jsonObject: JSONObject, key: String): String {
        try {
            return jsonObject.getString(key)
        } catch (e: JSONException) {
            return ""
        }

    }

}
