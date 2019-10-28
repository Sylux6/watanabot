package commands.general

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object ScreenShareCommand : AbstractCommand("screenshare") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get a link for a screen sharing session for the current voice channel."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!event.member!!.voiceState!!.inVoiceChannel()) {
            sendMessage(event.channel, "Please join a voice channel")
            return
        }
        sendMessage(event.channel,
                "There you go for screen sharing:\n <https://www.discordapp.com/channels/"
                        + "${event.guild.id}/${event.member!!.voiceState!!.channel!!.id}>")
    }
}