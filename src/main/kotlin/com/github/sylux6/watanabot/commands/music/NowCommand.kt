package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.bot.PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.message.sendMessage
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object NowCommand : AbstractCommand("now") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Show the title of the current song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = MusicCommandModule.getGuildAudioPlayer(event.guild)
        val track = musicManager.player.playingTrack ?: throw CommandException("Nothing is played")
        val embedTrack = EmbedBuilder()
            .setTitle("\uD83C\uDFB6 ${track.info.title}", track.info.uri)
            .setAuthor("Now playing")
            .setColor(PRIMARY_COLOR)
            .build()
        sendMessage(event.channel, embedTrack)
    }
}