package commands.music

import commands.music.MusicCommandModule.getGuildAudioPlayer
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage


object ShuffleCommand : AbstractCommand("shuffle") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Shuffle playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = getGuildAudioPlayer(event.guild)
        musicManager.scheduler.shuffle()
        sendMessage(event.channel, "Playlist has been shuffled")
    }
}