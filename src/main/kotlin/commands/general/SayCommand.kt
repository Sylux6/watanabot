package commands.general

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object SayCommand : AbstractCommand("say") {
    override val template: String
        get() = "[<message>]"
    override val description: String
        get() = "Make the bot repeat what you say."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        sendMessage(event.channel,args.joinToString(" "))
    }
}