package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULES
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ModulesCommand : AbstractCommand("modules") {
    override val template: String
        get() = ""
    override val description: String
        get() = "List available modules."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val embedMessage = EmbedBuilder()
            .setColor(BOT_PRIMARY_COLOR)
            .setTitle("Available modules")
            .setDescription("See documentation for each of them with `--help`")
        for (module in COMMAND_MODULES.sortedBy { module -> module.name }) {
            if (module.restrict) {
                continue
            }
            embedMessage.addField("${module.name} (`${module.shortName}`)", module.moduleDescription, false)
        }
        sendMessage(event.channel, embedMessage.build())
    }
}
