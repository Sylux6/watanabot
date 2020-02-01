package com.github.sylux6.watanabot.modules.music

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.music.commands.ClearCommand
import com.github.sylux6.watanabot.modules.music.commands.JoinCommand
import com.github.sylux6.watanabot.modules.music.commands.LeaveCommand
import com.github.sylux6.watanabot.modules.music.commands.NextCommand
import com.github.sylux6.watanabot.modules.music.commands.NowCommand
import com.github.sylux6.watanabot.modules.music.commands.PauseCommand
import com.github.sylux6.watanabot.modules.music.commands.PlayCommand
import com.github.sylux6.watanabot.modules.music.commands.PlaylistCommand
import com.github.sylux6.watanabot.modules.music.commands.QueueCommand
import com.github.sylux6.watanabot.modules.music.commands.ResumeCommand
import com.github.sylux6.watanabot.modules.music.commands.ShuffleCommand
import com.github.sylux6.watanabot.modules.music.commands.StopCommand
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
        JoinCommand,
        LeaveCommand,
        PlayCommand,
        StopCommand,
        PauseCommand,
        ResumeCommand,
        ShuffleCommand,
        NextCommand,
        PlaylistCommand,
        ClearCommand,
        QueueCommand,
        NowCommand
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