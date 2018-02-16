package modules.llsif;

import java.util.concurrent.TimeUnit;

import modules.AbstractModule;
import modules.llsif.entity.Idol;
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
	commands.put("info", (event, args) -> {
	    if (args.size() < 2)
		return;
	    for (Idol i : Idol.values()) {
		for (int j = 1; j < args.size(); j++) {
		    if (i.toString().matches("(.*(?i)"+args.get(j)+" .*)")) {
			BotUtils.sendMessageEmbed(event.getChannel(), i.toEmbed());
			return;
		    }
		}
	    }
	    BotUtils.sendMessage(event.getChannel(), "Idol not found");
	});
	
    }

    @Override
    public String getName() {
	return "(ll)sif";
    }

}
