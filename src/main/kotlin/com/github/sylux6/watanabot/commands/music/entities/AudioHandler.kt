package com.github.sylux6.watanabot.commands.music.entities

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.MessageChannel
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage

class AudioHandler(private val musicManager: GuildMusicManager, private val channel: MessageChannel, private val track: String) : AudioLoadResultHandler {

    override fun trackLoaded(track: AudioTrack) {
        sendMessage(channel, "Adding to queue ${track.info.title}")
        musicManager.scheduler.queue(track)
    }

    override fun playlistLoaded(playlist: AudioPlaylist) {
        var track: AudioTrack? = playlist.selectedTrack

        if (track != null) {
            // Users picked a track from a playlist: only load this one
            track = playlist.tracks[0]
            musicManager.scheduler.queue(track)
            sendMessage(channel, "Adding to queue ${track!!.info.title} (first track of playlist ${playlist.name})")
        } else {
            // Load all the tracks from the playlist
            for (t in playlist.tracks)
                musicManager.scheduler.queue(t)

            sendMessage(channel, "Adding to queue all tracks from playlist ${playlist.name}")
        }
    }

    override fun noMatches() {
        sendMessage(channel, "Nothing found by $track")
    }

    override fun loadFailed(e: FriendlyException) {
        sendMessage(channel, "Could not play: ${e.message}")
    }

}
