package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.EmbedUtils
import com.github.sylux6.watanabot.utils.MessageUtils
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

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