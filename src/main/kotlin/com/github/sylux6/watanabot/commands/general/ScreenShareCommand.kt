package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ScreenShareCommand : AbstractCommand("screenshare", levelAccess = listOf(CommandLevelAccess.IN_VOICE)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get a link for a screen sharing session for the current voice channel."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!event.member!!.voiceState!!.inVoiceChannel()) {
            sendMessage(event.channel, "Please join a voice channel")
            return
        }
        sendMessage(
            event.channel,
            "There you go for screen sharing:\n <https://www.discordapp.com/channels/"
                + "${event.guild.id}/${event.member!!.voiceState!!.channel!!.id}>"
        )
    }
}