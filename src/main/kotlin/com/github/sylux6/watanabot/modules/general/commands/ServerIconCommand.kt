package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.buildBotMessage
import com.github.sylux6.watanabot.utils.buildEmbedImageOnly
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ServerIconCommand : AbstractCommand("icon") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get server icon."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val iconUrl = event.guild.iconUrl
        if (iconUrl == null) {
            sendMessage(event.channel, buildBotMessage("No icon found"))
            return
        }
        sendMessage(event.channel, buildEmbedImageOnly(event.guild.name, iconUrl))
    }
}