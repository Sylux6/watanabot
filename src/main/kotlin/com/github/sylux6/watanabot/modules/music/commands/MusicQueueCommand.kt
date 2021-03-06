package com.github.sylux6.watanabot.modules.music.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.modules.music.MusicCommandModule
import com.github.sylux6.watanabot.modules.music.entities.AudioHandler
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object MusicQueueCommand : AbstractCommand("queue", 1, levelAccess = listOf(CommandLevelAccess.IN_VOICE_WITH_BOT)) {
    override val template: String
        get() = "<link>"
    override val description: String
        get() = "Enqueue song from link to the playlist."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager =
            MusicCommandModule.getGuildAudioPlayer(event.guild)

        if (args.isEmpty() && musicManager.scheduler.tracklist.size > 1) {
            musicManager.player.startTrack(musicManager.scheduler.tracklist.first(), true)
            return
        }

        MusicCommandModule.playerManager.loadItemOrdered(
            musicManager,
            args.first(),
            AudioHandler(musicManager, event.channel, args.first())
        )
    }
}
