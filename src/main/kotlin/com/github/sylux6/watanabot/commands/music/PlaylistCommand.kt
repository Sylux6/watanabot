package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.commands.music.MusicCommandModule.getGuildAudioPlayer
import com.github.sylux6.watanabot.commands.music.entities.GuildMusicManager
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.bot.PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.message.sendMessage
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object PlaylistCommand : AbstractCommand("playlist") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Show current playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val embedMessage = EmbedBuilder()
            .setColor(PRIMARY_COLOR)
            .setAuthor("Playlist")
        val message = StringBuilder()
        val musicManager: GuildMusicManager = getGuildAudioPlayer(event.guild)
        val playlist: List<AudioTrack> = musicManager.scheduler.tracklist
        for ((index, track) in playlist.withIndex()) {
            if (index > 5) {
                message.append("\nand **${playlist.size - index}** more...")
                break
            }
            message.append("**${index + 1}.** ${track.info.title}\n")
        }
        val track = musicManager.player.playingTrack
        embedMessage.setTitle("\uD83C\uDFB6 ${track.info.title}", track.info.uri)
        embedMessage.setDescription(message.toString())
        sendMessage(event.channel, embedMessage.build())
    }
}