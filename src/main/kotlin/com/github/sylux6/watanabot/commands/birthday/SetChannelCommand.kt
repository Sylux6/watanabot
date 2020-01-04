package com.github.sylux6.watanabot.commands.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.models.Settings
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.DBUtils.query
import com.github.sylux6.watanabot.utils.DBUtils.saveOrUpdate
import com.github.sylux6.watanabot.utils.MessageUtils.linkTextChannel
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessageAt

object SetChannelCommand : AbstractCommand("setchannel", 1) {
    override val template: String
        get() = "<channel>"
    override val description: String
        get() = "Set text channel to announce birthdays."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!event.member?.hasPermission(Permission.MANAGE_CHANNEL)!!) {
            sendMessageAt(event.channel, event.author,
                    "You have no permission to set a birthday channel")
            return
        }
        val settings = Settings(event.guild.idLong, event.channel.idLong)
        val res = query("select id from settings where guildid = ${event.guild.id}")
        if (res.isNotEmpty()) {
            settings.id = res[0] as Int
        }
        saveOrUpdate(settings)
        sendMessage(event.channel, "Birthdays will be announced in "
                + linkTextChannel(event.channel)
                + " (note that I'll check your birthdays everyday on CEST timezone (> ᴗ •)ゞ)")
    }
}