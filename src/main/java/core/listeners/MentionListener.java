package core.listeners;

import core.BotUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.handle.obj.IEmoji;

public class MentionListener {

    @EventSubscriber
    public void onMentionEvent(MentionEvent event) {
	IEmoji yousoro;
	    yousoro = event.getGuild().getEmojiByName("yousoro");
	    if (yousoro != null)
		BotUtils.sendMessage(event.getChannel(), "<@" + event.getAuthor().getLongID() + "> " + BotUtils.getEmoji(event.getGuild(), "yousoro"));
	    else
		BotUtils.sendMessage(event.getChannel(), "<@" + event.getAuthor().getLongID() + "> Yousoro!~");
	
    }
}
