package commands.general

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.EmbedUtils
import utils.MessageUtils.sendMessage

object LewdCommand : AbstractCommand("lewd") {
    override val template: String
        get() = ""
    override val description: String
        get() = "I'm not LEWD."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        sendMessage(event.channel, EmbedUtils.buildEmbedImageOnly("https://i.imgur.com/VGXNX82.jpg"))
    }
}