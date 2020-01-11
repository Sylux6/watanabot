package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.commands.music.MusicCommandModule.getGuildAudioPlayer
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import net.dv8tion.jda.api.events.message.MessageReceivedEvent


object PauseCommand : AbstractCommand("pause", levelAccess = listOf(CommandLevelAccess.IN_VOICE_WITH_BOT)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Pause the current song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = getGuildAudioPlayer(event.guild)
        musicManager.player.isPaused = true
    }
}