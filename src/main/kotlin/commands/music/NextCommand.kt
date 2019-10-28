package commands.music

import commands.music.MusicCommandModule.getGuildAudioPlayer
import commands.music.entities.GuildMusicManager
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage


object NextCommand : AbstractCommand("next") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Skip to the next song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager: GuildMusicManager = getGuildAudioPlayer(event.guild)
        musicManager.scheduler.nextTrack()
        sendMessage(event.channel, "Playing next track: ${musicManager.player.playingTrack.info.title}")
    }
}