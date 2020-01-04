package com.github.sylux6.watanabot.threads

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class ThreadCommand(private val commandModule: AbstractCommandModule, private val command: AbstractCommand,
                    internal var event: MessageReceivedEvent, var argTab: List<String>) : Runnable {

    override fun run() {
        command.preRunCommand(commandModule, event, argTab)
    }

}
