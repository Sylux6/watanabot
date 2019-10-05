package utils;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.utils.IOUtil;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

public class MessageUtils {

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
        BotUtils.bot.getGuildById(BotUtils.SRID).getTextChannelsByName("log", true).get(0).sendMessage(message).queue();
    }

    static public void sendFile(@NotNull MessageChannel channel, byte[] file, String attachment, Message message) {
        channel.sendMessage(message).addFile(file, attachment).queue();
    }

    static public void sendFileByUrl(MessageChannel channel, String message, String url) throws IOException {
        URL url_ = new URL(url);
        byte[] file = IOUtil.readFully(url_.openStream());
        MessageBuilder m = new MessageBuilder(message);
        channel.sendMessage(m.build()).addFile(file, FilenameUtils.getName(url_.getPath())).queue();
    }

    static public void sendDM(User user, String message) {
	PrivateChannel channel = user.openPrivateChannel().complete();
	channel.sendMessage(message).queue();
    }

    static public void editMessage(Message oldMessage, String newMessage) {
	oldMessage.editMessage(newMessage).queue();
    }

    static public void editMessage(Message oldMessage, Message newMessage) {
	oldMessage.editMessage(newMessage).queue();
    }

    static public void editMessage(Message oldMessage, MessageEmbed newMessage) {
	oldMessage.editMessage(newMessage).queue();
    }

    /**
     * Function for reacting to a message for a given String emote
     * @param message message to react
     * @param name guild name
     * @return true on success
     */
    static public boolean reactMessage(Message message, String name) {
	Emote emote = BotUtils.getEmote(message.getGuild(), name, false);
	if (emote != null) {
	    message.addReaction(emote).queue();
	    return true;
	}
	return false;
    }

    /**
     * Returns mention syntax for message
     * @param user user
     * @return mention syntax
     */
    static public String mentionAt(User user) {
	return "<@" + user.getId() + ">";
    }

    /**
     * Returns text channel syntax for message
     * @param channel MessageChannel
     * @return TextChannel link syntax
     */
    static public String linkTextChannel(MessageChannel channel) {
        return "<#" + channel.getId() + ">";
    }
}
