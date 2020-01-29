package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.bot.getEmojiMessage
import com.github.sylux6.watanabot.utils.message.mentionAt
import com.github.sylux6.watanabot.utils.message.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import kotlin.random.Random

object RollCommand : AbstractCommand("roll") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Roll a random number between 1 and 100."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        sendMessage(
            event.channel,
            "${mentionAt(event.author)} ${Random.nextInt(
                100
            ) + 1} "
                + getEmojiMessage(event.guild, "yousoro")
        )
    }
}