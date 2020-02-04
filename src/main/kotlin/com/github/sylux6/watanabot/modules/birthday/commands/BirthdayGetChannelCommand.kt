package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.linkTextChannel
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Settings
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object BirthdayGetChannelCommand : AbstractCommand("getchannel") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get text channel in where birthdays are announced."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val birthdayChannelId: Long = transaction {
            Settings
                .slice(Settings.birthdayChannelId)
                .select { Settings.guildId eq event.guild.idLong }
                .firstOrNull()?.get(Settings.birthdayChannelId) ?: throw CommandException("Birthday channel is not set")
        }
        val birthdayChannelName = event.guild.getTextChannelById(birthdayChannelId)!!
        sendMessage(event.channel, "Birthdays are announced in " + linkTextChannel(birthdayChannelName))
    }
}
