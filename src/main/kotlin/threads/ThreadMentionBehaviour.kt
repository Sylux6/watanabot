package threads

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils
import utils.MessageUtils

class ThreadMentionBehaviour(event: MessageReceivedEvent) : ThreadGeneralBehaviour(event) {

    override fun run() {
        message = event.message.contentDisplay
        val answer = StringBuilder(MessageUtils.mentionAt(event.author) + " ")

        if (message.matches(".*(?i)lewd.*".toRegex())) {
            MessageUtils.sendMessage(event.channel, answer.append("I'm not lewd!").toString())
        } else {
            if (BotUtils.yousoroEmojiExists(event.guild)) {
                MessageUtils.sendMessage(event.channel, answer.append(BotUtils.getEmojiMessage(event.guild, "yousoro")).toString())
            } else {
                MessageUtils.sendMessage(event.channel, answer.append("(> ᴗ •)ゞ aaaa").toString())
            }
        }

    }

}
