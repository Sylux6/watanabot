package internal.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils
import utils.MessageUtils

abstract class AbstractCommandModule(val name: String, val shortName: String, commands: Set<AbstractCommand>,
                                     val restrict: Boolean = false) {

    abstract val moduleDescription: String

    val commandMap: MutableMap<String, AbstractCommand> = HashMap()

    init {
        commandMap["--help"] = object : AbstractCommand("help") {
            override val template: String
                get() = ""
            override val description = "Get list of commands"

            override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
                val message = EmbedBuilder()
                        .setThumbnail(BotUtils.bot.selfUser.avatarUrl)
                        .setColor(BotUtils.PRIMARY_COLOR)
                        .setTitle("List of commands")
                        .setDescription(moduleDescription)
                for ((commandName, command) in commandMap.toSortedMap()) {
                    if (command.privateAccess && event.guild.idLong != BotUtils.SRID)
                        continue
                    if (commandName == "--help")
                        continue
                    message.addField("`$commandName`", command.description, false)
                }
                MessageUtils.sendMessage(event.channel, message.build())
            }
        }
        for (command in commands) {
            commandMap[command.name] = command
        }
    }
}