package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ChooseCommand : AbstractCommand("choose") {
    override val template: String
        get() = "[<option>]..."
    override val description: String
        get() = "Choose something among the options."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val introMsg = arrayOf(
            "Obviously",
            "I take",
            "The best would be"
        )
        sendMessage(event.channel, introMsg.random() + " **" + args.random() + "**")
    }
}
