package com.github.sylux6.watanabot.modules.music.entities

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

/**
 * Holder for both the player and a track scheduler for one guild.
 */
class GuildMusicManager
/**
 * Creates a player and a track scheduler.
 *
 * @param manager
 * Audio player com.github.sylux6.watanabot.manager to use for creating the player.
 */
    (manager: AudioPlayerManager) {
    /**
     * Audio player for the guild.
     */
    val player: AudioPlayer = manager.createPlayer()
    /**
     * Track scheduler for the player.
     */
    val scheduler: TrackScheduler

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    val sendHandler: AudioPlayerSendHandler
        get() = AudioPlayerSendHandler(player)

    init {
        scheduler = TrackScheduler(player)
        player.addListener(scheduler)
        player.volume = 50
    }
}
