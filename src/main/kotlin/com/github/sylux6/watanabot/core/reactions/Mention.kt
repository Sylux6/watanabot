package com.github.sylux6.watanabot.core.reactions

import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

fun botMention(event: MessageReceivedEvent) {
    if (event.message.contentDisplay.contains("lewd", true)) {
        sendMessage(event.channel, "${mentionAt(event.author)} I'm not lewd!")
    } else {
        sendMessage(event.channel, "${mentionAt(event.author)} ${getYousoro(event.guild)}")
    }
}
