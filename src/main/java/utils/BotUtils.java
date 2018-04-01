package utils;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BotUtils {
    
    // RNG
    static public Random random = new Random();

    // CONSTANTS
    static public String BOT_PREFIX = "$";
    static public int PREFIX_LENGTH = BOT_PREFIX.length();
    static public int NB_THREAD = 50;
    
    // Logger 
    static public Logger logger = LoggerFactory.getLogger("Watanabot");
    
    /////////////////////////////////////////////
    ////////      FUNCTIONS         /////////////
    /////////////////////////////////////////////
    
    
    ///////// SENDING MESSAGE FUNCTION ////////
    
    static public void sendMessage(MessageChannel channel, String message) {
	channel.sendMessage(message).queue();
    }
    
    static public void sendMessage(MessageChannel channel, Message message) {
	channel.sendMessage(message).queue();
    }
    
    static public void sendMessage(MessageChannel channel, MessageEmbed message) {
	channel.sendMessage(message).queue();
    }
    
    static public void sendFile(MessageChannel channel, byte[] file, String attachment, Message message) {
	channel.sendFile(file, attachment, message).queue();
    }
    
    ///////// EDITING MESSAGE FUNCTION ////////
    
    static public void editMessage(Message oldMessage, String newMessage) {
	oldMessage.editMessage(newMessage).queue();
    }
    
    static public void editMessage(Message oldMessage, Message newMessage) {
	oldMessage.editMessage(newMessage).queue();
    }  
    
    static public void editMessage(Message oldMessage, MessageEmbed newMessage) {
	oldMessage.editMessage(newMessage).queue();
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
    
    // Returns emoji object from guild
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
