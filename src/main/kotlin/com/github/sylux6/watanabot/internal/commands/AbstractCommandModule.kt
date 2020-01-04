package com.github.sylux6.watanabot.internal.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.BotUtils
import com.github.sylux6.watanabot.utils.MessageUtils

abstract class AbstractCommandModule(val name: String, val shortName: String, commands: Set<AbstractCommand>,
                                     val restrict: Boolean = false) {

    abstract val moduleDescription: String

    val commandMap: MutableMap<String, AbstractCommand> = HashMap()

    init {
        commandMap["--help"] = object : AbstractCommand("help") {
            override val template: String
                get() = ""
            override val description = "Get list of com.github.sylux6.watanabot.commands"

            override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
                val message = EmbedBuilder()
                        .setAuthor(BotUtils.bot.selfUser.name, null, BotUtils.bot.selfUser.effectiveAvatarUrl)
                        .setColor(BotUtils.PRIMARY_COLOR)
                        .setTitle("List of com.github.sylux6.watanabot.commands")
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