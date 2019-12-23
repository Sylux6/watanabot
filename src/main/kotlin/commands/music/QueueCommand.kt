package commands.music

import commands.music.entities.AudioHandler
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils

object QueueCommand : AbstractCommand("queue", 1) {
    override val template: String
        get() = "<link>"
    override val description: String
        get() = "Enqueue song from link to the playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!MusicCommandModule.isInVoiceChannel(event.member!!, event.guild.audioManager)) {
            MessageUtils.sendMessage(event.channel, "You must be in the voice channel")
            return
        }

        val musicManager = MusicCommandModule.getGuildAudioPlayer(event.guild)
        val audioManager = event.guild.audioManager

        // Check if bot is in voiceChannel
        if (!audioManager.isConnected) {
            MessageUtils.sendMessage(event.channel, "I am not in a voice channel. Please make me join you.")
            return
        }

        if (args.isEmpty() && musicManager.scheduler.tracklist.size > 1) {
            musicManager.player.startTrack(musicManager.scheduler.tracklist.first(), true)
            return
        }

        MusicCommandModule.playerManager.loadItemOrdered(musicManager, args.first(),
                AudioHandler(musicManager, event.channel, args.first()))
    }
}