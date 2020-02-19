package com.github.sylux6.watanabot.modules.music.entities

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager

val playerManager: AudioPlayerManager = DefaultAudioPlayerManager()

val playerByGuild: Map<Long, GuildPlayerManager> = mapOf()