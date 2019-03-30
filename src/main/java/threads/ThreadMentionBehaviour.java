package threads;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.BotUtils;

public class ThreadMentionBehaviour extends ThreadGeneralBehaviour {

    public ThreadMentionBehaviour(MessageReceivedEvent event) {
	super(event);
    }

    @Override
    public void run() {
	message = event.getMessage().getContentDisplay();
	StringBuilder answer = new StringBuilder(BotUtils.mentionAt(event.getAuthor()) + " ");

	if (message.matches(".*(?i)lewd.*")) {
	    BotUtils.sendMessage(event.getChannel(), answer.append("I'm not lewd!").toString());
	}
	else {
	    if (BotUtils.yousoroEmojiExists(event.getGuild())) {
		BotUtils.sendMessage(event.getChannel(), answer.append(BotUtils.getEmojiMessage(event.getGuild(), "yousoro")).toString());
	    } else {
		BotUtils.sendMessage(event.getChannel(), answer.append("(> ᴗ •)ゞ").toString());
	    }	    
	}
	
    }

}
