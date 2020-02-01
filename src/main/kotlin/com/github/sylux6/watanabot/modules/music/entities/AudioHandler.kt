package com.github.sylux6.watanabot.modules.music.entities

import com.github.sylux6.watanabot.internal.types.BotMessageType
import com.github.sylux6.watanabot.utils.sendBotMessage
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.MessageChannel

class AudioHandler(
    private val musicManager: GuildMusicManager,
    private val channel: MessageChannel,
    private val track: String
) : AudioLoadResultHandler {

    override fun trackLoaded(track: AudioTrack) {
        sendBotMessage(channel, "Music player", "Adding to queue ${track.info.title}")
        musicManager.scheduler.queue(track)
    }

    override fun playlistLoaded(playlist: AudioPlaylist) {
        var track: AudioTrack? = playlist.selectedTrack

        if (track != null) {
            // Users picked a track from a playlist: only load this one
            track = playlist.tracks[0]
            musicManager.scheduler.queue(track)
            sendBotMessage(
                channel,
                "Music player",
                "Adding to queue ${track!!.info.title} (first track of playlist ${playlist.name})"
            )
        } else {
            // Load all the tracks from the playlist
            for (t in playlist.tracks) {
                musicManager.scheduler.queue(t)
            }
            sendBotMessage(channel, "Music player", "Adding to queue all tracks from playlist ${playlist.name}")
        }
    }

    override fun noMatches() {
        sendBotMessage(channel, "Music player", "Nothing found by $track", BotMessageType.ERROR)
    }

    override fun loadFailed(e: FriendlyException) {
        sendBotMessage(channel, "Music player", "Could not play: ${e.message}", BotMessageType.ERROR)
    }
}
