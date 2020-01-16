package com.github.sylux6.watanabot.modules.music.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.modules.music.MusicCommandModule.getGuildAudioPlayer
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object LeaveCommand : AbstractCommand("leave", levelAccess = listOf(CommandLevelAccess.BOT_IN_VOICE)) {
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