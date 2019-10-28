package commands.music

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import commands.music.MusicCommandModule.getGuildAudioPlayer
import commands.music.entities.GuildMusicManager
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils.PRIMARY_COLOR
import utils.MessageUtils.sendMessage


object PlaylisCommand : AbstractCommand("playlist") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Show current playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val embedMessage = EmbedBuilder()
                .setColor(PRIMARY_COLOR)
                .setTitle("Playlist")
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
        embedMessage.setDescription(message.toString())
        sendMessage(event.channel, embedMessage.build())
    }
}