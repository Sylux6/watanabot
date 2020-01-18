package com.github.sylux6.watanabot.threads

import com.github.sylux6.watanabot.utils.MessageUtils
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

open class ThreadGeneralBehaviour(internal var event: MessageReceivedEvent) : Runnable {
    internal lateinit var message: String

    override fun run() {
        message = event.message.contentDisplay

        // Let's go for matching all possible cases
        // We don't use else if since we want to match multiple cases

        if (message.matches("(.*(?i)yousoro.*)|(.*sylux6Yo.*)".toRegex())) {
            if (!MessageUtils.reactMessage(event.message, "yousoro")) {
                // Blue heart
                event.message.addReaction("\uD83D\uDC99").queue()
            }
        }

        when {
            message.matches("(.*\\W+|)((?i)zensoku+ +zenshi+n+)(\\W+.*|)".toRegex()) -> MessageUtils.sendMessage(
                event.channel,
                "YOUSORO!~ (> ᴗ •)ゞ"
            )
            message.matches("(.*\\W+|)((?i)best +waifu)(\\W+.*|)".toRegex()) -> MessageUtils.sendMessage(
                event.channel,
                "わたし？"
            )
            message.matches("(?i).*hello.*|.*ohayo.*|.*good +morning.*".toRegex()) -> MessageUtils.sendMessage(
                event.channel,
                MessageUtils.mentionAt(event.author) + " Ohayousoro! (> ᴗ •)ゞ"
            )
        }
    }
}
