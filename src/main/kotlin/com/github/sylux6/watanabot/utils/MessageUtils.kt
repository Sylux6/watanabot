package com.github.sylux6.watanabot.utils

import com.github.sylux6.watanabot.internal.types.BotMessageType
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.internal.utils.IOUtil
import org.apache.commons.io.FilenameUtils
import java.io.IOException
import java.net.URL

fun sendBotMessage(channel: MessageChannel, message: String, type: BotMessageType = BotMessageType.NORMAL) {
    channel.sendMessage(buildBotMessage(message, type)).queue()
}

fun sendMessage(channel: MessageChannel, message: String) {
    channel.sendMessage(message).queue()
}

fun sendMessage(channel: MessageChannel, message: Message) {
    channel.sendMessage(message).queue()
}

fun sendMessage(channel: MessageChannel, message: MessageEmbed) {
    channel.sendMessage(message).queue()
}

fun sendMessageAt(channel: MessageChannel, user: User, message: String) {
    channel.sendMessage(mentionAt(user) + " " + message).queue()
}

fun sendMessageAt(channel: MessageChannel, user: User, message: Message) {
    channel.sendMessage(mentionAt(user) + " " + message).queue()
}

fun sendMessageAt(channel: MessageChannel, user: User, message: MessageEmbed) {
    channel.sendMessage(mentionAt(user) + " " + message).queue()
}

fun sendLog(message: String) {
    bot.getGuildById(SRID)!!.getTextChannelsByName("log", true)[0].sendMessage(message).queue()
}

fun sendLog(message: MessageEmbed) {
    bot.getGuildById(SRID)!!.getTextChannelsByName("log", true)[0].sendMessage(message).queue()
}

fun sendFile(channel: MessageChannel, file: ByteArray, attachment: String, message: Message) {
    channel.sendMessage(message).addFile(file, attachment).queue()
}

@Throws(IOException::class)
fun sendFileByUrl(channel: MessageChannel, message: String, url: String) {
    val url_ = URL(url)
    val file = IOUtil.readFully(url_.openStream())
    val m = MessageBuilder(message)
    channel.sendMessage(m.build()).addFile(file, FilenameUtils.getName(url_.path)).queue()
}

fun sendDM(user: User, message: String) {
    val channel = user.openPrivateChannel().complete()
    channel.sendMessage(message).queue()
}

fun editMessage(oldMessage: Message, newMessage: String) {
    oldMessage.editMessage(newMessage).queue()
}

fun editMessage(oldMessage: Message, newMessage: Message) {
    oldMessage.editMessage(newMessage).queue()
}

fun editMessage(oldMessage: Message, newMessage: MessageEmbed) {
    oldMessage.editMessage(newMessage).queue()
}

/**
 * Function for reacting to a message for a given String emote
 * @param message message to react
 * @param name guild name
 * @return true on success
 */
fun reactMessage(message: Message, name: String): Boolean {
    val emote = getEmote(message.guild, name, false)
    if (emote != null) {
        message.addReaction(emote).queue()
        return true
    }
    return false
}

/**
 * Returns mention syntax for message
 * @param user user
 * @return mention syntax
 */
fun mentionAt(user: User): String {
    return "<@" + user.id + ">"
}

/**
 * Returns text channel syntax for message
 * @param channel MessageChannel
 * @return TextChannel link syntax
 */
fun linkTextChannel(channel: MessageChannel): String {
    return "<#" + channel.id + ">"
}
