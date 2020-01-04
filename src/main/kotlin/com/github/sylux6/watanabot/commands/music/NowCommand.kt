package com.github.sylux6.watanabot.commands.music

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.BotUtils
import com.github.sylux6.watanabot.utils.EmbedUtils
import com.github.sylux6.watanabot.utils.MessageUtils

object NowCommand : AbstractCommand("now") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Show the title of the current song."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val musicManager = MusicCommandModule.getGuildAudioPlayer(event.guild)
        val track = musicManager.player.playingTrack
        if (track == null) {
            MessageUtils.sendMessage(event.channel, EmbedUtils.buildBotMessage("Nothing is played"))
            return
        }
        val embedTrack = EmbedBuilder()
                .setTitle("\uD83C\uDFB6 ${track.info.title}", track.info.uri)
                .setAuthor("Now playing")
                .setColor(BotUtils.PRIMARY_COLOR)
                .build()
        MessageUtils.sendMessage(event.channel, embedTrack)
    }

}