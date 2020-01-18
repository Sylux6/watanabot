package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.commands.music.entities.GuildMusicManager
import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.HashMap

object MusicCommandModule : AbstractCommandModule(
    "Music",
    "m",
    setOf(
        JoinCommand, LeaveCommand, PlayCommand, StopCommand, PauseCommand, ResumeCommand, ShuffleCommand,
        NextCommand, PlaylistCommand, ClearCommand, QueueCommand, NowCommand
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