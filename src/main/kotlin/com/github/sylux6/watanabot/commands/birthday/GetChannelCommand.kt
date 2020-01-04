package com.github.sylux6.watanabot.commands.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.DBUtils.query
import com.github.sylux6.watanabot.utils.MessageUtils.linkTextChannel
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage

object GetChannelCommand : AbstractCommand("getchannel") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get text channel in where birthdays are announced."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val res = query("select birthdaychannelid from settings where guildid = ${event.guild.id}")
        val channel = event.guild.getTextChannelById(res[0].toString())
        if (res.isEmpty() || channel == null) {
            sendMessage(event.channel, "Birthday channel is not set")
            return
        }
        sendMessage(event.channel, "Birthdays are announced in " + linkTextChannel(channel))
    }
}