package commands.music

import internal.commands.AbstractCommand
import commands.music.MusicCommandModule.getGuildAudioPlayer
import commands.music.MusicCommandModule.isInVoiceChannel
import commands.music.MusicCommandModule.playerManager
import commands.music.entities.AudioHandler
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object PlayCommand : AbstractCommand("play") {
    override val template: String
        get() = "[<link>]"
    override val description: String
        get() = "Play a song from link or resume playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!isInVoiceChannel(event.member!!, event.guild.audioManager)) {
            sendMessage(event.channel, "You must be in the voice channel")
            return
        }

        val musicManager = getGuildAudioPlayer(event.guild)
        val audioManager = event.guild.audioManager

        // Check if bot is in voiceChannel
        if (!audioManager.isConnected) {
            sendMessage(event.channel, "I am not in a voice channel. Please make me join you.")
            return
        }

        if (args.isEmpty() && musicManager.scheduler.tracklist.size > 1) {
            musicManager.player.startTrack(musicManager.scheduler.tracklist.first(), true)
            return
        }

        musicManager.player.stopTrack()
        musicManager.scheduler.purgeQueue()
        playerManager.loadItemOrdered(musicManager, args.first(), AudioHandler(musicManager, event.channel, args.first()))
    }
}