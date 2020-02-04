package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.sendBotMessage
import db.models.Members
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object BirthdayRemoveCommand : AbstractCommand("remove") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Remove your birthday."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        transaction {
            Members.update({ Members.guildId eq event.guild.idLong and (Members.userId eq event.author.idLong) }) {
                it[birthday] = null
            }
        }
        sendBotMessage(event.channel, message = "**${event.member!!.effectiveName}** birthday has been removed")
    }
}
