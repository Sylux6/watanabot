package commands.general

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.EmbedUtils
import utils.MessageUtils

object ServerIconCommand : AbstractCommand("icon") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get server icon."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val iconUrl = event.guild.iconUrl
        if (iconUrl == null) {
            MessageUtils.sendMessage(event.channel, EmbedUtils.buildBotMessage("No icon found"))
            return
        }
        MessageUtils.sendMessage(event.channel, EmbedUtils.buildEmbedImageOnly(event.guild.name, iconUrl))
    }

}