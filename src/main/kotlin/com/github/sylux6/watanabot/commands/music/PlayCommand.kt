package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.commands.music.MusicCommandModule.getGuildAudioPlayer
import com.github.sylux6.watanabot.commands.music.MusicCommandModule.playerManager
import com.github.sylux6.watanabot.commands.music.entities.AudioHandler
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object PlayCommand : AbstractCommand("play", levelAccess = listOf(CommandLevelAccess.IN_VOICE_WITH_BOT)) {
    override val template: String
        get() = "[<link>] [--f]"
    override val description: String
        get() = "Play a song from link or resume playlist. Use `--f` option to force playing a new song if bot is already playing."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = getGuildAudioPlayer(event.guild)

        if (args.isEmpty() && musicManager.scheduler.tracklist.size > 1) {
            musicManager.player.startTrack(musicManager.scheduler.tracklist.first(), true)
            return
        }

        musicManager.player.stopTrack()
        musicManager.scheduler.purgeQueue()
        playerManager.loadItemOrdered(musicManager, args.first(), AudioHandler(musicManager, event.channel, args.first()))
    }
}