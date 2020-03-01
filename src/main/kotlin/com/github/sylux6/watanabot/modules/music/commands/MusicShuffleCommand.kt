package com.github.sylux6.watanabot.modules.music.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.modules.music.MusicCommandModule.getGuildAudioPlayer
import com.github.sylux6.watanabot.utils.sendBotMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object MusicShuffleCommand : AbstractCommand("shuffle", levelAccess = listOf(CommandLevelAccess.IN_VOICE_WITH_BOT)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Shuffle playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = getGuildAudioPlayer(event.guild)
        musicManager.scheduler.shuffle()
        sendBotMessage(event.channel, "Music player", message = "Playlist has been shuffled")
    }
}
