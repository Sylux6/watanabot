package threads;


import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.MessageUtils;

public class ThreadGeneralBehaviour implements Runnable {

    MessageReceivedEvent event;
    String message;

    public ThreadGeneralBehaviour(MessageReceivedEvent event) {
	this.event = event;
    }

    @Override
    public void run() {
	message = event.getMessage().getContentDisplay();

	// Let's go for matching all possible cases
	// We don't use else if since we want to match multiple cases

	if (message.matches("(.*(?i)yousoro.*)|(.*sylux6Yo.*)")) {
	    if (!MessageUtils.reactMessage(event.getMessage(), "yousoro")) {
	    	// Blue heart
			event.getMessage().addReaction("\uD83D\uDC99").queue();
	    }
	}
	
	if (message.matches("(.*\\W+|)((?i)zensoku+ +zenshi+n+)(\\W+.*|)")) {
	    MessageUtils.sendMessage(event.getChannel(), "YOUSORO!~ (> ᴗ •)ゞ");
	}
	
	else if (message.matches("(.*\\W+|)((?i)best +waifu)(\\W+.*|)")) {
	    MessageUtils.sendMessage(event.getChannel(), "わたし？");
	}
	
	else if (message.matches("(?i).*hello.*|.*ohayo.*|.*good +morning.*")) {
	    MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " Ohayousoro! (> ᴗ •)ゞ");
	}
	
    }

}
