package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object SayCommand : AbstractCommand("say") {
    override val template: String
        get() = "[<message>]"
    override val description: String
        get() = "Make the bot repeat what you say."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        sendMessage(event.channel, args.joinToString(" "))
    }
}