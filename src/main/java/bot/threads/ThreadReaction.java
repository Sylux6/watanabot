package bot.threads;

import com.vdurmont.emoji.EmojiManager;

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
	IEmoji yousoro;
	if (message.matches("(.*(?i)yousoro.*)|(.*sylux6Yo.*)")) {
	    yousoro = event.getGuild().getEmojiByName("yousoro");
	    if (yousoro != null)
		event.getMessage().addReaction(yousoro);
	    else
		event.getMessage().addReaction(EmojiManager.getForAlias("heart"));;
	}
    }

}
