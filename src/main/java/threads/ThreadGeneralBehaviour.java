package threads;


import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.BotUtils;

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
	    if (!BotUtils.reactMessage(event.getMessage(), "yousoro")) {
		event.getMessage().addReaction("\uD83D\uDC99").queue();
	    }
	}
	
	if (message.matches("(.*\\W+|)((?i)zensoku+ +zenshi+n+)(\\W+.*|)")) {
	    BotUtils.sendMessage(event.getChannel(), "YOUSORO!~ (> ᴗ •)ゞ");
	}
	
	else if (message.matches("(.*\\W+|)((?i)best +waifu)(\\W+.*|)")) {
	    BotUtils.sendMessage(event.getChannel(), "わたし？");
	}
	
	else if (message.matches("(?i).*hello.*|.*ohayo.*|.*good +morning.*")) {
	    BotUtils.sendMessage(event.getChannel(), BotUtils.mentionAt(event.getAuthor()) + " Ohayousoro! (> ᴗ •)ゞ");
	}
	
    }

}
