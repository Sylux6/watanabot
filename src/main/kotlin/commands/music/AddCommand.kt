package commands.music

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import commands.music.MusicCommandModule.getGuildAudioPlayer
import commands.music.MusicCommandModule.isInVoiceChannel
import commands.music.MusicCommandModule.playerManager
import commands.music.entities.AudioHandler
import utils.MessageUtils.sendMessage


object AddCommand : AbstractCommand("add", 1) {
    override val template: String
        get() = "<link>"
    override val description: String
        get() = "Add song from link to the playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!isInVoiceChannel(event.member!!, event.guild.audioManager)) {
            sendMessage(event.channel, "You must be in the voice channel")
            return
        }
        val musicManager = getGuildAudioPlayer(event.guild)
        val audioManager = event.guild.audioManager

        // Check if bot is in voiceChannel
        if (!audioManager.isConnected) {
            sendMessage(event.channel, "I am not in a voice channel. Please make me join you.")
            return
        }

        playerManager.loadItemOrdered(musicManager, args.first(), AudioHandler(musicManager, event.channel, args.first()))
    }
}