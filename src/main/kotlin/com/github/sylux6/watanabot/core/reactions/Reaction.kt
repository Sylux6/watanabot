package com.github.sylux6.watanabot.core.reactions

import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.reactMessage
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

fun botReaction(event: MessageReceivedEvent) {
    val message = event.message.contentDisplay
    if (message.contains("yousoro", true) || message.contains("sylux6Yo", false)) {
        if (!reactMessage(event.message, "yousoro")) {
            event.message.addReaction("\uD83D\uDC99").queue()
        }
    }

    when {
        message.contains("zensoku zenshin", true) -> sendMessage(event.channel, "YOUSORO!~ (> ᴗ •)ゞ")
        message.contains("best waifu", true) -> sendMessage(event.channel, "わたし？")
        message.contains("hello", true) || message.contains("ohayo", true) || message.contains(
            "good morning",
            true
        ) -> sendMessage(event.channel, "${mentionAt(event.author)} Ohayousoro! (> ᴗ •)ゞ")
    }
}
