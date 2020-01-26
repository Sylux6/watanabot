package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.bot
import com.github.sylux6.watanabot.utils.sendBotMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import kotlin.system.exitProcess

object ShutdownCommand : AbstractCommand("shutdown", levelAccess = listOf(CommandLevelAccess.OWNER)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Shutdown the bot."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Any {
        sendBotMessage(event.channel, "Shutting down bot...")
        bot.shutdown()
        exitProcess(0)
    }
}