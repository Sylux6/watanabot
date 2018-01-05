package modules.picture;

import java.util.Collections;

import core.BotUtils;
import modules.AbstractModule;
import net.kodehawa.lib.imageboards.DefaultImageBoards;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException;

public class PictureModule extends AbstractModule {

    @Override
    public void populate() {
	commands.put("get", (event, args) -> {

	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "I dont know what to get for you");
		return;
	    }
	    try {
		DefaultImageBoards.SAFEBOORU.search(100, args.get(1)).async(images -> {
		    Collections.shuffle(images);
		    BotUtils.sendMessage(event.getChannel(), images.get(0).getURL());
		});
	    } catch (QueryParseException e) {
		BotUtils.sendMessage(event.getChannel(), "I can't find");
	    }

	});
    }

}
