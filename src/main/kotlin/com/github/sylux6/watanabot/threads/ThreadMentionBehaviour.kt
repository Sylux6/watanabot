package com.github.sylux6.watanabot.threads

import com.github.sylux6.watanabot.utils.bot.getEmojiMessage
import com.github.sylux6.watanabot.utils.message.mentionAt
import com.github.sylux6.watanabot.utils.message.sendMessage
import com.github.sylux6.watanabot.utils.bot.yousoroEmojiExists
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class ThreadMentionBehaviour(event: MessageReceivedEvent) : ThreadGeneralBehaviour(event) {

    override fun run() {
        message = event.message.contentDisplay
        val answer = StringBuilder(mentionAt(event.author) + " ")

        if (message.matches(".*(?i)lewd.*".toRegex())) {
            sendMessage(
                event.channel,
                answer.append("I'm not lewd!").toString()
            )
        } else {
            if (yousoroEmojiExists(event.guild)) {
                sendMessage(
                    event.channel,
                    answer.append(
                        getEmojiMessage(
                            event.guild,
                            "yousoro"
                        )
                    ).toString()
                )
            } else {
                sendMessage(
                    event.channel,
                    answer.append("(> ᴗ •)ゞ aaaa").toString()
                )
            }
        }
    }
}
