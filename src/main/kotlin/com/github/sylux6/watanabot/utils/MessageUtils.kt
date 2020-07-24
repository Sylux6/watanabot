package com.github.sylux6.watanabot.utils

import com.github.sylux6.watanabot.internal.types.BotMessageType
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.internal.utils.IOUtil
import org.apache.commons.io.FilenameUtils
import java.io.IOException
import java.net.URL

val DISCORD_TAG = Regex("\\w+?#\\d{4}")
val DISCORD_MENTION = Regex("<@(!\\d{17}|\\d{18})>")
val DISCORD_CHANNEL = Regex("<#{18}>")
val DISCORD_ROLE = Regex("<@&\\d{18}>")

fun sendBotMessage(
    channel: MessageChannel,
    title: String? = null,
    message: String,
    type: BotMessageType = BotMessageType.NORMAL,
    thumbnail: String? = null
) {
    channel.sendMessage(buildBotMessage(title, message, type, thumbnail)).queue()
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

fun sendLog(message: String, type: BotMessageType = BotMessageType.NORMAL) {
    PRIVATE_SERVER_ID?.let { jdaInstance.getGuildById(it) }!!.getTextChannelsByName("log", true)[0].sendMessage(
        buildBotMessage(
            message = message,
            type = type
        )
    ).queue()
}

fun sendLog(message: MessageEmbed) {
    PRIVATE_SERVER_ID?.let { jdaInstance.getGuildById(it) }!!.getTextChannelsByName("log", true)[0].sendMessage(message).queue()
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
    user.openPrivateChannel().queue() { channel ->
        channel.sendMessage(message).queue()
    }
}

fun sendDM(user: User, message: MessageEmbed) {
    user.openPrivateChannel().queue() { channel ->
        channel.sendMessage(message).queue()
    }
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
 * Returns mention syntax for message
 * @param users list of users
 * @return mention syntax
 */
fun mentionAt(users: Set<User>): String {
    val result = StringBuilder()
    for (user in users) {
        result.append("<@${user.id}> ")
    }
    return result.toString()
}

/**
 * Returns mention syntax for message
 * @param member member
 * @return mention syntax
 */
fun mentionAt(member: Member): String {
    return "<@" + member.id + ">"
}

/**
 * Returns text channel syntax for message
 * @param channel MessageChannel
 * @return TextChannel link syntax
 */
fun linkTextChannel(channel: MessageChannel): String {
    return "<#" + channel.id + ">"
}
