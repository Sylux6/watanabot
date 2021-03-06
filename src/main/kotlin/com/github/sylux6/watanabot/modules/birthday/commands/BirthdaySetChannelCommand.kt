package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.linkTextChannel
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Guilds
import db.utils.insertOrUpdate
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.transactions.transaction

object BirthdaySetChannelCommand : AbstractCommand("setchannel", levelAccess = listOf(CommandLevelAccess.MOD)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Set the current text channel to announce birthdays."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        transaction {
            Guilds.insertOrUpdate(Guilds.guildId) {
                it[guildId] = event.guild.idLong
                it[birthdayChannelId] = event.channel.idLong
            }
        }
        sendMessage(
            event.channel,
            "Birthdays will be announced in " +
                linkTextChannel(event.channel) +
                " (note that I'll check your birthdays everyday on CEST timezone (> ᴗ •)ゞ)"
        )
    }
}
