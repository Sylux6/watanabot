package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object DebugCommand : AbstractCommand("debug", levelAccess = listOf(CommandLevelAccess.OWNER)) {
    override val template: String
        get() = ""
    override val description: String
        get() = ""

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
    }
}
