package commands.music

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import internal.commands.AbstractCommandModule
import commands.music.entities.GuildMusicManager
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.managers.AudioManager
import java.util.*

object MusicCommandModule : AbstractCommandModule(
        "Music",
        "m",
        setOf(JoinCommand, LeaveCommand, PlayCommand, StopCommand, PauseCommand, ResumeCommand, ShuffleCommand,
                NextCommand, PlaylisCommand, ClearCommand, QueueCommand, NowCommand)
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
        var musicManager: GuildMusicManager? = musicManagers[guildId]

        if (musicManager == null) {
            musicManager = GuildMusicManager(playerManager)
            musicManager.player.volume = 50
            musicManagers[guildId] = musicManager
        }

        guild.audioManager.sendingHandler = musicManager.sendHandler

        return musicManager
    }

    fun isInVoiceChannel(author: Member, audioManager: AudioManager): Boolean {
        return if (author.voiceState!!.channel == null || audioManager.connectedChannel == null) false
        else author.voiceState!!.channel == audioManager.connectedChannel
    }

    fun embedTrackInfo(track: AudioTrack): MessageEmbed {
        return EmbedBuilder()
                .setTitle("\uD83C\uDFB6 ${track.info.title}", track.info.uri)
                .build()
    }
}