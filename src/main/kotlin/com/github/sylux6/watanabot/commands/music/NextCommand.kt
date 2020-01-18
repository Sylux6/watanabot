package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.commands.music.MusicCommandModule.getGuildAudioPlayer
import com.github.sylux6.watanabot.commands.music.entities.GuildMusicManager
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object NextCommand : AbstractCommand("next", levelAccess = listOf(CommandLevelAccess.IN_VOICE_WITH_BOT)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Skip to the next song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager: GuildMusicManager = getGuildAudioPlayer(event.guild)
        musicManager.scheduler.nextTrack()
        sendMessage(event.channel, "Playing next track: ${musicManager.player.playingTrack.info.title}")
    }
}