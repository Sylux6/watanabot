package com.github.sylux6.watanabot.commands.love_live

import com.github.sylux6.watanabot.commands.love_live.entities.Idol
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage

object InfoCommand : AbstractCommand("info", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get details about an idol."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        for (idol in Idol.values()) {
            for (term in args) {
                if ("""(.*(?i)$term.*)""".toRegex().matches(idol.toString())) {
                    sendMessage(event.channel, idol.toEmbed())
                    return
                }
            }
        }
        sendMessage(event.channel, "Idol not found")
    }
}