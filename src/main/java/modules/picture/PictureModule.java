package modules.picture;

import java.util.Collections;
import java.util.List;

import core.BotUtils;
import modules.AbstractModule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.kodehawa.lib.imageboards.DefaultImageBoards;
import net.kodehawa.lib.imageboards.entities.Rating;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException;

public class PictureModule extends AbstractModule {

    @Override
    public void populate() {
	commands.put("get", (event, args) -> {

	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "I do not know what to get for you");
		return;
	    }
	    try {
		DefaultImageBoards.DANBOORU.search(args.get(1)).async(images -> {
		    if (!images.isEmpty()) {
			int i = 0;
			Collections.shuffle(images);
			while (!images.get(i).getRating().equals(Rating.SAFE))
			    i++;
			BotUtils.sendMessage(event.getChannel(), images.get(i).getURL());
		    } else
			BotUtils.sendMessage(event.getChannel(), "I can't find");
		});
	    } catch (QueryParseException e) {
		BotUtils.sendMessage(event.getChannel(), "I can't find");
	    }

	});
    }

    public void getByDefault(MessageReceivedEvent event, List<String> args) {
	try {
	    DefaultImageBoards.DANBOORU.search(args.get(0)).async(images -> {
		if (!images.isEmpty()) {
		    int i = 0;
		    Collections.shuffle(images);
		    while (!images.get(i).getRating().equals(Rating.SAFE))
			i++;
		    BotUtils.sendMessage(event.getChannel(), images.get(i).getURL());
		} else
		    BotUtils.sendMessage(event.getChannel(), "I can't find");
	    });
	} catch (QueryParseException e) {
	    BotUtils.sendMessage(event.getChannel(), "I can't find");
	}
    }

}
