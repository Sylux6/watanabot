package commands.music

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object JoinCommand : AbstractCommand("join") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Make Watanabot join your voice channel."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val audioManager = event.guild.audioManager

        // Check if bot is not currently in use
        if (audioManager.isConnected || audioManager.isAttemptingToConnect) {
            sendMessage(event.channel, "I'm already in a voice channel")
            return
        }
        audioManager.openAudioConnection(event.member?.voiceState?.channel)
        sendMessage(event.channel, "Ohayousoro!~ (> ᴗ •)ゞ")
    }
}