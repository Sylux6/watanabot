package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.commands.music.MusicCommandModule.getGuildAudioPlayer
import com.github.sylux6.watanabot.commands.music.MusicCommandModule.isInVoiceChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.MessageUtils


object PauseCommand : AbstractCommand("pause") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Pause the current song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (!isInVoiceChannel(event.member!!, event.guild.audioManager)) {
            MessageUtils.sendMessage(event.channel, "You must be in the voice channel")
            return
        }
        val musicManager = getGuildAudioPlayer(event.guild)
        musicManager.player.isPaused = true
        MessageUtils.sendMessage(event.channel, "Pause...")
    }
}