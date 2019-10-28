package threads;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.BotUtils;
import utils.MessageUtils;

public class ThreadMentionBehaviour extends ThreadGeneralBehaviour {

    public ThreadMentionBehaviour(MessageReceivedEvent event) {
	super(event);
    }

    @Override
    public void run() {
	message = event.getMessage().getContentDisplay();
	StringBuilder answer = new StringBuilder(MessageUtils.mentionAt(event.getAuthor()) + " ");

	if (message.matches(".*(?i)lewd.*")) {
	    MessageUtils.sendMessage(event.getChannel(), answer.append("I'm not lewd!").toString());
	}
	else {
	    if (BotUtils.yousoroEmojiExists(event.getGuild())) {
		MessageUtils.sendMessage(event.getChannel(), answer.append(BotUtils.getEmojiMessage(event.getGuild(), "yousoro")).toString());
	    } else {
		MessageUtils.sendMessage(event.getChannel(), answer.append("(> ᴗ •)ゞ").toString());
	    }	    
	}
	
    }

}
