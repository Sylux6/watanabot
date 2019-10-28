package commands.music

import internal.commands.AbstractCommand
import commands.music.MusicCommandModule.getGuildAudioPlayer
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage


object StopCommand : AbstractCommand("stop") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Stop playing the current song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = getGuildAudioPlayer(event.guild)
        musicManager.player.stopTrack()
        sendMessage(event.channel, "Stop playing")
    }
}