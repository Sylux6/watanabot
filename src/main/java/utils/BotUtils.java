package utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.core.entities.*;
import org.apache.commons.io.FilenameUtils;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.utils.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotUtils {
    
    // Bot
    static public JDA bot;
    
    // RNG
    static public Random random = new Random();

    // CONSTANTS
    static public String BOT_PREFIX = "o7";
    static public int PREFIX_LENGTH = BOT_PREFIX.length();
    static public int NB_THREAD = 50;
    
    // SECRET ROOM RELATED
    static public long SRID = Long.parseLong("181478842274283520");

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

    static public void sendMessageAt(MessageChannel channel, User user, String message) {
        channel.sendMessage(mentionAt(user) + " " + message).queue();
    }

    static public void sendMessageAt(MessageChannel channel, User user, Message message) {
        channel.sendMessage(mentionAt(user) + " " + message).queue();
    }

    static public void sendMessageAt(MessageChannel channel, User user, MessageEmbed message) {
        channel.sendMessage(mentionAt(user) + " " + message).queue();
    }

    static public void sendLog(String message) {
        bot.getGuildById(SRID).getTextChannelsByName("log", true).get(0).sendMessage(message).queue();
    }
    
    static public void sendFile(MessageChannel channel, byte[] file, String attachment, Message message) {
	channel.sendFile(file, attachment, message).queue();
    }
    
    static public void sendFileByUrl(MessageChannel channel, String message, String url) throws IOException {
	URL url_ = new URL(url);
	byte[] file = IOUtil.readFully(url_.openStream());
	MessageBuilder m = new MessageBuilder(message);
	channel.sendFile(file, FilenameUtils.getName(url_.getPath()), m.build()).queue();
    }
    
    static public void sendDM(User user, String message) {
	PrivateChannel channel = user.openPrivateChannel().complete();
	channel.sendMessage(message).queue();
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
    
    ///////// ROLE FUNCTION ////////
    static public void addRole(Guild guild, Member member, Role role) {
	guild.getController().addRolesToMember(member, role).queue();
    }
    
    static public void removeRole(Guild guild, Member member, Role role) {
	guild.getController().removeRolesFromMember(member, role).queue();
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
    
    static public String getYousoro(Guild guild) {
	Emote emote = getEmote(guild, "yousoro", false);
	if (emote == null)
	    return "(> ᴗ •)ゞ";
	return "<:yousoro:"+emote.getId()+">";
    }
    
    // Returns mention syntax for message
    static public String mentionAt(User user) {
	return "<@" + user.getId() + ">";
    }

    // Returns text channel syntax for message
    static public String linkTextChannel(MessageChannel channel) {
        return "<#" + channel.getId() + ">";
    }
}
