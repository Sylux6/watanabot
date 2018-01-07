package threads;

import core.BotUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ThreadMentionBehaviour extends ThreadGeneralBehaviour {

    public ThreadMentionBehaviour(MessageReceivedEvent event) {
	super(event);
    }

    @Override
    public void run() {
	message = event.getMessage().getContentDisplay();

	if (BotUtils.yousoroEmojiExists(event.getGuild())) {
	    BotUtils.sendMessage(event.getChannel(),
		    BotUtils.mentionAt(event.getAuthor()) + " " + BotUtils.getEmojiMessage(event.getGuild(), "yousoro"));
	} else {
	    BotUtils.sendMessage(event.getChannel(), BotUtils.mentionAt(event.getAuthor()) + " (> ᴗ •)ゞ");
	}
    }

}
