package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.linkTextChannel
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Settings
import db.utils.insertOrUpdate
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.transactions.transaction

object BirthdaySetChannelCommand : AbstractCommand("setchannel", 1, listOf(CommandLevelAccess.MOD)) {
    override val template: String
        get() = "<channel>"
    override val description: String
        get() = "Set text channel to announce birthdays."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        transaction {
            Settings.insertOrUpdate(Settings.guildId) {
                it[guildId] = event.guild.idLong
                it[birthdayChannelId] = event.channel.idLong
            }
        }
        sendMessage(
            event.channel, "Birthdays will be announced in " +
                linkTextChannel(event.channel) +
                " (note that I'll check your birthdays everyday on CEST timezone (> ᴗ •)ゞ)"
        )
    }
}
