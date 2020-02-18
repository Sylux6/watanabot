package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Users
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.jodatime.month
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object BirthdayMonthCommand : AbstractCommand("month") {
    override val template: String
        get() = "[<month>]"
    override val description: String
        get() = "Get birthdays from the current or a given month."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val month = if (args.isEmpty()) LocalDate.now().monthValue else args[0].toInt()

        if (month < 1 || month > 12) {
            throw CommandException("Invalid month")
        }

        val birthdays: Map<Int, List<Member>> = transaction {
            Users
                .slice(Users.userId, Users.birthday)
                .select { Users.birthday.month() eq month }
                .orderBy(Users.birthday)
                .groupBy { it[Users.birthday]!!.dayOfMonth }
                .mapValues { (_, users) -> users.mapNotNull { user -> event.guild.getMemberById(user[Users.userId]) } }
        }
        birthdayInMonthEmbedMessage(
            event.channel,
            month,
            birthdays
        )
    }

    private fun birthdayInMonthEmbedMessage(channel: MessageChannel, month: Int, birthdays: Map<Int, List<Member>>) {
        val message = EmbedBuilder()
            .setColor(BOT_PRIMARY_COLOR)
            .setTitle("Birthdays in ${Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH)}")
        if (birthdays.isEmpty()) {
            message.setDescription("No birthday")
            sendMessage(channel, message.build())
            return
        }
        for ((day, members) in birthdays) {
            if (members.isNotEmpty()) {
                message.addField(
                    "\uD83C\uDF82 $day/${DecimalFormat("00").format(month)}",
                    members.joinToString("\n") { it.effectiveName },
                    false
                )
            }
        }
        sendMessage(channel, message.build())
    }
}
