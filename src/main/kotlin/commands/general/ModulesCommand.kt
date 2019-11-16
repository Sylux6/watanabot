package commands.general

import core.CommandHandler.COMMAND_MODULES
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils.PRIMARY_COLOR
import utils.MessageUtils.sendMessage

object ModulesCommand : AbstractCommand("modules") {
    override val template: String
        get() = ""
    override val description: String
        get() = "List available modules."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val embedMessage = EmbedBuilder()
                .setColor(PRIMARY_COLOR)
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