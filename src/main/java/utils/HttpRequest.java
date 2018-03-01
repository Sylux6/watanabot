package utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {
    
    private static OkHttpClient client = new OkHttpClient();
    
    static public String getRequest(String url, String... values) throws IOException {
	
	StringBuilder s = new StringBuilder(url+"?");
	for(String val : values)
	    s.append(val+"&");
	
	Request request = new Request.Builder()
		.url(s.toString())
		.build();
	
	Response response =  client.newCall(request).execute();
	return response.body().string();
    }
    
    
}
