package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.models.Settings
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.linkTextChannel
import com.github.sylux6.watanabot.utils.query
import com.github.sylux6.watanabot.utils.saveOrUpdate
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object BirthdaySetChannelCommand : AbstractCommand("setchannel", 1, listOf(CommandLevelAccess.MOD)) {
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
            event.channel, "Birthdays will be announced in " +
                linkTextChannel(event.channel) +
                " (note that I'll check your birthdays everyday on CEST timezone (> ᴗ •)ゞ)"
        )
    }
}
