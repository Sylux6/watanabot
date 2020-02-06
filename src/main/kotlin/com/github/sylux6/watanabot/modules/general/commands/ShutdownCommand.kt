package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.sendBotMessage
import kotlin.system.exitProcess
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ShutdownCommand : AbstractCommand("shutdown", levelAccess = listOf(CommandLevelAccess.OWNER)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Shutdown the bot."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Any {
        sendBotMessage(event.channel, message = "Shutting down bot...")
        jda.shutdown()
        exitProcess(0)
    }
}
