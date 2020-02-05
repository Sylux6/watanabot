package com.github.sylux6.watanabot.modules.music

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.music.commands.MusicClearCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicJoinCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicLeaveCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicNextCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicNowCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicPauseCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicPlayCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicPlaylistCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicQueueCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicResumeCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicShuffleCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicStopCommand
import com.github.sylux6.watanabot.modules.music.entities.GuildMusicManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.HashMap

object MusicCommandModule : AbstractCommandModule(
    "Music",
    "m",
    setOf(
        MusicJoinCommand,
        MusicLeaveCommand,
        MusicPlayCommand,
        MusicStopCommand,
        MusicPauseCommand,
        MusicResumeCommand,
        MusicShuffleCommand,
        MusicNextCommand,
        MusicPlaylistCommand,
        MusicClearCommand,
        MusicQueueCommand,
        MusicNowCommand
    )
) {
    override val moduleDescription: String
        get() = "Commands for listening to music."

    val playerManager = DefaultAudioPlayerManager()
    private val musicManagers = HashMap<Long, GuildMusicManager>()

    init {
        AudioSourceManagers.registerRemoteSources(playerManager)
        AudioSourceManagers.registerLocalSource(playerManager)
    }

    @Synchronized
    fun getGuildAudioPlayer(guild: Guild): GuildMusicManager {
        val guildId = java.lang.Long.parseLong(guild.id)
        var musicManager = musicManagers[guildId]

        if (musicManager == null) {
            musicManager = GuildMusicManager(playerManager)
            musicManagers[guildId] = musicManager
        }

        guild.audioManager.sendingHandler = musicManager.sendHandler

        return musicManager
    }

    fun isInBotVoiceChannel(event: MessageReceivedEvent): Boolean {
        val member = event.member!!
        val audioManager = event.guild.audioManager
        return if (member.voiceState!!.channel == null || audioManager.connectedChannel == null) false
        else member.voiceState!!.channel == audioManager.connectedChannel
    }
}