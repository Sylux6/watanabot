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
	commands.put("nsfw", (event, args) -> {
	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "I do not know what to get for you");
		return;
	    }
	    if (event.getTextChannel().isNSFW())
		getImage(event, String.join(" ", args.subList(1, args.size())), Rating.EXPLICIT);
	    else
		BotUtils.sendMessage(event.getChannel(), "You are not in a nsfw channel. You lewd!");
	});

	commands.put("safe", (event, args) -> {
	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "I do not know what to get for you");
		return;
	    }
	    getImage(event, String.join(" ", args.subList(1, args.size())), Rating.SAFE);
	});
    }

    static public void getByDefault(MessageReceivedEvent event, List<String> args) {
	getImage(event, args);
    }

    static public void getImage(MessageReceivedEvent event, List<String> search) {
	getImage(event, String.join(" ", search), event.getTextChannel().isNSFW() ? Rating.EXPLICIT : Rating.SAFE);
    }

    static public void getImage(MessageReceivedEvent event, String search, Rating rating) {
	String rating_part;
	switch (rating) {
	case EXPLICIT:
	    rating_part = "rating:explicit";
	    break;
	case QUESTIONABLE:
	    rating_part = "rating:questionnable";
	    break;
	default:
	    rating_part = "rating:safe";
	}
	try {
	    DefaultImageBoards.DANBOORU.search(100, String.join(" ", search + " " + rating_part)).async(images -> {
		// The last rating tag is taken during search process so user can not trick the
		// nsfw filter
		if (!images.isEmpty()) {
		    Collections.shuffle(images);
		    BotUtils.sendMessageEmbed(event.getChannel(),
			    EmbedUtils.buildEmbedImage(
				    images.get(0).getTag_string_character() + " drawn by "
					    + images.get(0).getTag_string_artist() + " - Danbooru",
				    images.get(0).getURL(), images.get(0).getTags(), images.get(0).getURL()));
		} else
		    BotUtils.sendMessage(event.getChannel(), "I can't find");
	    });
	} catch (QueryParseException e) {
	    BotUtils.sendMessage(event.getChannel(), "I can't find");
	}
    }

    @Override
    public String getName() {
	return "(p)icture";
    }

}