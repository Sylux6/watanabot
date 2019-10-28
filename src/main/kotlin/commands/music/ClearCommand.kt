package commands.music

import commands.music.MusicCommandModule.getGuildAudioPlayer
import commands.music.entities.GuildMusicManager
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage


object ClearCommand : AbstractCommand("clear") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Clear current playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!MusicCommandModule.isInVoiceChannel(event.member!!, event.guild.audioManager)) {
            sendMessage(event.channel, "You must be in the voice channel")
            return
        }
        val musicManager: GuildMusicManager = getGuildAudioPlayer(event.guild)
        musicManager.scheduler.purgeQueue()
        sendMessage(event.channel, "Playlist has been cleared")
    }
}