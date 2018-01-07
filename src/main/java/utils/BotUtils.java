package utils;

import java.util.List;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class BotUtils {

    // Constant prefix --CHANGE HERE--
    static public String BOT_PREFIX = "o7";
    static public int PREFIX_LENGTH = BOT_PREFIX.length();
    
    // Bot functions:
    
    // Function for sending message in the specified channel
    static public void sendMessage(MessageChannel channel, String message) {
	channel.sendMessage(message).queue();
    }
    
    // Function for sending message embed in the specified channel
    static public void sendMessageEmbed(MessageChannel channel, MessageEmbed message) {
	channel.sendMessage(message).queue();
    }
    
    // Function for reacting to a message for a given String emote
    // returns true on success
    static public boolean reactMessage(Message message, String name) {
	Emote emote = getEmote(message.getGuild(), name, false);
	if (emote != null) {
	    message.addReaction(emote).queue();
	    return true;
	}
	return false;
    }
    
    // Checks if a yousoro emoji exists
    static public boolean yousoroEmojiExists(Guild guild) {
	return !guild.getEmotesByName("yousoro", true).isEmpty();
    }
    
    // Return emoji object from guild
    static public Emote getEmote(Guild guild, String name, boolean ignoreCase) {
	List<Emote> emote = guild.getEmotesByName(name, ignoreCase);
	if (emote.isEmpty())
	    return null;
	return emote.get(0);
    }
    
    // Returns emoji syntax for message
    static public String getEmojiMessage(Guild guild, String name) {
	Emote emote = getEmote(guild, name, false);
	if (emote == null)
	    return "";
	return "<:"+name+":"+emote.getId()+">";
    }
    
    // Returns mention syntax for message
    static public String mentionAt(User user) {
	return "<@" + user.getId() + ">";
    }
    
}
