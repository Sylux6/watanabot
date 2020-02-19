package com.github.sylux6.watanabot.modules.music.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.modules.music.MusicCommandModule
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object MusicNowCommand : AbstractCommand("now") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Show the title of the current song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        // val musicManager =
        //     MusicCommandModule.getGuildAudioPlayer(event.guild)
        // val track = musicManager.player.playingTrack ?: commandFail("Nothing is played")
        // val embedTrack = EmbedBuilder()
        //     .setTitle("\uD83C\uDFB6 ${track.info.title}", track.info.uri)
        //     .setAuthor("Now playing", null, event.jda.selfUser.effectiveAvatarUrl)
        //     .setColor(BOT_PRIMARY_COLOR)
        //     .build()
        // sendMessage(event.channel, embedTrack)
    }
}
