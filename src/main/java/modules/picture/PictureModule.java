package modules.picture;

import java.util.Collections;
import java.util.List;

import modules.AbstractModule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.kodehawa.lib.imageboards.DefaultImageBoards;
import net.kodehawa.lib.imageboards.entities.Rating;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException;
import utils.BotUtils;
import utils.EmbedUtils;

public class PictureModule extends AbstractModule {

    @Override
    public void populate() {
	commands.put("get", (event, args) -> {

	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "I do not know what to get for you");
		return;
	    }
	    getImage(event, args.subList(1, args.size()));

	});
    }

    static public void getByDefault(MessageReceivedEvent event, List<String> args) {
	getImage(event, args);
    }
    
    static public void getImage(MessageReceivedEvent event, List<String> search) {
	getImage(event, String.join(" ", search));
    }

    static public void getImage(MessageReceivedEvent event, String search) {
	try {
	    DefaultImageBoards.DANBOORU.search(String.join(" ", search)).async(images -> {
		if (!images.isEmpty()) {
		    int i = 0;
		    Collections.shuffle(images);
		    while (!images.get(i).getRating().equals(Rating.SAFE))
			i++;
		    BotUtils.sendMessageEmbed(event.getChannel(),
			    EmbedUtils
				    .buildEmbedImage(
					    images.get(i).getTag_string_character() + " drawn by "
						    + images.get(i).getTag_string_artist() + " - Danbooru",
					    images.get(i).getURL(), images.get(i).getTags(), images.get(i).getURL())
				    .build());
		} else
		    BotUtils.sendMessage(event.getChannel(), "I can't find");
	    });
	} catch (QueryParseException e) {
	    BotUtils.sendMessage(event.getChannel(), "I can't find");
	}
    }

}
