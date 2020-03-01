package com.github.sylux6.watanabot.modules.poll.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.modules.poll.utils.sendPollMessage
import com.github.sylux6.watanabot.utils.buildBotMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object PollNew : AbstractCommand("new", 1) {
    override val template: String
        get() = "<topic> | <option 1> | <option 2> [ | <option 3> ... ]"
    override val description: String
        get() = "Create a new poll, up to 10 options."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val options = args.joinToString(" ").split("|").map { element -> element.trim() }
        if (options.size < 3 || options.size > 11) {
            throw CommandException("Please provide 2 to 10 options.")
        }
        sendPollMessage(event, buildBotMessage(message = "Making poll..."), 24, options.first(), options.drop(1), false)
    }
}
