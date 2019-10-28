package commands.music

import internal.commands.AbstractCommand
import commands.music.MusicCommandModule.getGuildAudioPlayer
import commands.music.MusicCommandModule.isInVoiceChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils


object ResumeCommand : AbstractCommand("resume") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Resume song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!isInVoiceChannel(event.member!!, event.guild.audioManager)) {
            MessageUtils.sendMessage(event.channel, "You must be in the voice channel")
            return
        }
        val musicManager = getGuildAudioPlayer(event.guild)
        musicManager.player.isPaused = false
        MessageUtils.sendMessage(event.channel, "... Resume")
    }
}