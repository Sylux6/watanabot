package com.github.sylux6.watanabot.modules.music.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object JoinCommand : AbstractCommand("join") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Make Watanabot join your voice channel."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val audioManager = event.guild.audioManager

        // Check if bot is not currently in use
        if (audioManager.isConnected || audioManager.isAttemptingToConnect) {
            throw CommandException("Bot is already in a voice channel")
        }
        audioManager.openAudioConnection(event.member?.voiceState?.channel)
        sendMessage(event.channel, "Ohayousoro!~ (> ᴗ •)ゞ")
    }
}