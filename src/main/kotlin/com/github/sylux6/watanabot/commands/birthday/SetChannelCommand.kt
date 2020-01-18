package com.github.sylux6.watanabot.commands.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.models.Settings
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.DBUtils.query
import com.github.sylux6.watanabot.utils.DBUtils.saveOrUpdate
import com.github.sylux6.watanabot.utils.MessageUtils.linkTextChannel
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object SetChannelCommand : AbstractCommand("setchannel", 1, listOf(CommandLevelAccess.MOD)) {
    override val template: String
        get() = "<channel>"
    override val description: String
        get() = "Set text channel to announce birthdays."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val settings = Settings(event.guild.idLong, event.channel.idLong)
        val res = query("select id from settings where guildid = ${event.guild.id}")
        if (res.isNotEmpty()) {
            settings.id = res[0] as Int
        }
        saveOrUpdate(settings)
        sendMessage(
            event.channel, "Birthdays will be announced in "
                + linkTextChannel(event.channel)
                + " (note that I'll check your birthdays everyday on CEST timezone (> ᴗ •)ゞ)"
        )
    }
}