package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.dayFormatter
import com.github.sylux6.watanabot.utils.findMember
import com.github.sylux6.watanabot.utils.query
import com.github.sylux6.watanabot.utils.sendMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object BirthdayGetCommand : AbstractCommand("get") {
    override val template: String
        get() = "[<member>]"
    override val description: String
        get() = "Get birthday of a member."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        // No member provided, get self birthday
        val member = if (args.isEmpty()) {
            event.member ?: throw CommandException("You must run this command in a guild")
        } else {
            val username = args.joinToString(" ")
            findMember(event.guild, username) ?: throw CommandException("Cannot find **$username** in this server")
        }

        val res = query("select birthday from member where userid = ${member.user.id} and guildid = ${event.guild.id}")
        if (res.isNotEmpty() && res[0] != null) {
            birthdayEmbedMessage(
                event.channel,
                member,
                res[0] as Date
            )
        } else {
            throw CommandException("**${member.effectiveName}** didn't set a birthday")
        }
    }

    private fun birthdayEmbedMessage(channel: MessageChannel, member: Member, date: Date) {
        val message = EmbedBuilder()
            .setColor(PRIMARY_COLOR)
            .setAuthor(member.effectiveName, null, member.user.effectiveAvatarUrl)
            .setDescription("\uD83C\uDF82 ${dayFormatter(SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date))}")
        sendMessage(channel, message.build())
    }
}
