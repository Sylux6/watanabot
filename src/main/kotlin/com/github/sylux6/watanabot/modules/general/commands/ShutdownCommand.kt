package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.log
import com.github.sylux6.watanabot.utils.sendBotMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import kotlin.system.exitProcess

object ShutdownCommand : AbstractCommand("shutdown", levelAccess = listOf(CommandLevelAccess.OWNER)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Shutdown the bot."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Any {
        sendBotMessage(event.channel, message = "Shutting down bot...")
        event.jda.httpClient.connectionPool().evictAll()
        event.jda.shutdown()
        logger.log("Shutdown by ${event.author.asTag} with id ${event.author.id}")
        exitProcess(0)
    }
}
