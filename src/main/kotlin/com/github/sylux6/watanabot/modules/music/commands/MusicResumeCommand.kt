package com.github.sylux6.watanabot.modules.music.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.modules.music.MusicCommandModule.getGuildAudioPlayer
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object MusicResumeCommand : AbstractCommand("resume", levelAccess = listOf(CommandLevelAccess.IN_VOICE_WITH_BOT)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Resume song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = getGuildAudioPlayer(event.guild)
        musicManager.player.isPaused = false
    }
}
