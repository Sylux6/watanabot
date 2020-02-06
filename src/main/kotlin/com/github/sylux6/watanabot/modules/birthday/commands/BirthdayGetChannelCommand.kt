package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.linkTextChannel
import com.github.sylux6.watanabot.utils.query
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object BirthdayGetChannelCommand : AbstractCommand("getchannel") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get text channel in where birthdays are announced."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val res = query("select birthdaychannelid from settings where guildid = ${event.guild.id}")
        val channel = event.guild.getTextChannelById(res[0].toString())
        if (res.isEmpty() || channel == null) {
            throw CommandException("Birthday channel is not set")
        }
        sendMessage(event.channel, "Birthdays are announced in " + linkTextChannel(channel))
    }
}
