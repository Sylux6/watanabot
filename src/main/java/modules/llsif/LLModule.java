package modules.llsif;

import java.util.concurrent.TimeUnit;

import modules.AbstractModule;
import okhttp3.OkHttpClient;
import utils.BotUtils;

public class LLModule extends AbstractModule {
    
    private OkHttpClient client;
    
    public LLModule() {
	super();
	client = new OkHttpClient.Builder()
		.connectTimeout(3, TimeUnit.SECONDS)
	        .readTimeout(3, TimeUnit.SECONDS)
		.build();
    }

    @Override
    public void populate() {
	commands.put("scout", (event, args) -> {
	    BotUtils.sendMessage(event.getChannel(), "test scout");
	});
	
    }

    @Override
    public String getName() {
	return "(ll)sif";
    }

}
