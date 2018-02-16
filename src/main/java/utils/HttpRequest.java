package utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {
    
    private static OkHttpClient client = new OkHttpClient();
    
    static public String getRequest(String url) throws IOException {
	Request request = new Request.Builder()
		.url(url)
		.build();
	
	Response response =  client.newCall(request).execute();
	return response.body().string();
    }
    
    
}
