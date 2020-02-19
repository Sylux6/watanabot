package com.github.sylux6.watanabot.modules.music.entities

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildPlayerManager(private val manager: AudioPlayerManager) {
    val player: AudioPlayer = manager.createPlayer()
    val scheduler: TrackScheduler = TrackScheduler()

    init {
        player.addListener(scheduler)
    }
}