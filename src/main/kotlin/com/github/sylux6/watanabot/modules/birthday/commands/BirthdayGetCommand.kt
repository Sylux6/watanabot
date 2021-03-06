package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.dayFormatter
import com.github.sylux6.watanabot.utils.findMemberOrNull
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Users
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object BirthdayGetCommand : AbstractCommand("get") {
    override val template: String
        get() = "[<member>]"
    override val description: String
        get() = "Get birthday of a member."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        // No member provided, get self birthday
        val member = if (args.isEmpty()) {
            event.member ?: commandFail("You must run this command in a guild")
        } else {
            val username = args.joinToString(" ")
            findMemberOrNull(event.guild, username) ?: commandFail("Cannot find **$username** in this server")
        }

        val birthday: DateTime = transaction {
            Users
                .slice(Users.birthday)
                .select { Users.userId eq member.idLong }
                .singleOrNull()?.get(Users.birthday)
                ?: commandFail("**${member.effectiveName}** didn't set a birthday")
        }
        birthdayEmbedMessage(event.channel, member, birthday.toDate())
    }

    private fun birthdayEmbedMessage(channel: MessageChannel, member: Member, date: Date) {
        val message = EmbedBuilder()
            .setColor(BOT_PRIMARY_COLOR)
            .setAuthor(member.effectiveName, null, member.user.effectiveAvatarUrl)
            .setDescription("\uD83C\uDF82 ${dayFormatter(SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date))}")
        sendMessage(channel, message.build())
    }
}
