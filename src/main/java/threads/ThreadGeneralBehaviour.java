package threads;


import core.BotUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ThreadGeneralBehaviour implements Runnable {

    MessageReceivedEvent event;
    String message;

    public ThreadGeneralBehaviour(MessageReceivedEvent event) {
	this.event = event;
    }

    @Override
    public void run() {
	message = event.getMessage().getContentDisplay();

	// Let's go for the if... else if...

	if (message.matches("(.*(?i)yousoro.*)|(.*sylux6Yo.*)")) {
	    if (!BotUtils.reactMessage(event.getMessage(), "yousoro")) {
		event.getMessage().addReaction("\uD83D\uDC99").queue();
	    }
	}

	else if (message.matches(".*(?i)lewd.*")) {
	    BotUtils.sendMessage(event.getChannel(), "I'm not lewd!");
	}

    }

}
