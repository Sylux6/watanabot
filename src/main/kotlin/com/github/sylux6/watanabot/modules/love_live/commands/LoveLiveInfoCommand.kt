package com.github.sylux6.watanabot.modules.love_live.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.modules.love_live.entities.Idol
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object LoveLiveInfoCommand : AbstractCommand("info", 1) {
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
