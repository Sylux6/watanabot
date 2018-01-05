package bot.threads;

import com.vdurmont.emoji.EmojiManager;

import bot.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IEmoji;

public class ThreadReaction implements Runnable {
    
    MessageReceivedEvent event;
    String message;
    
    public ThreadReaction(MessageReceivedEvent event) {
	this.event = event;
    }

    @Override
    public void run() {
	message = event.getMessage().getContent();
		
	// Let's go for the if... else if...
	
	if (message.matches("(.*(?i)yousoro.*)|(.*sylux6Yo.*)")) {
	    IEmoji yousoro;
	    yousoro = event.getGuild().getEmojiByName("yousoro");
	    if (yousoro != null)
		event.getMessage().addReaction(yousoro);
	    else
		event.getMessage().addReaction(EmojiManager.getForAlias("heart"));
	}
	
	else if (message.matches(".*(?i)lewd.*")) {
	    BotUtils.sendMessage(event.getChannel(), "I'm not lewd!");
	}
	
    }

}
