package commands.music

import internal.commands.AbstractCommand
import commands.music.MusicCommandModule.getGuildAudioPlayer
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object LeaveCommand : AbstractCommand("leave") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Make Watanabot leave the voice channel."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        event.guild.audioManager.closeAudioConnection()
        getGuildAudioPlayer(event.guild).player.destroy()
        sendMessage(event.channel, "Bye bye!~ (> ᴗ •)ゞ")
    }
}