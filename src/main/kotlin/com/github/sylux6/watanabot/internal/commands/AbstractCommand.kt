package com.github.sylux6.watanabot.internal.commands

import com.github.sylux6.watanabot.commands.general.GeneralCommandModule
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.BotUtils
import com.github.sylux6.watanabot.utils.MessageUtils

abstract class AbstractCommand(val name: String, private val minArgs: Int = 0, val privateAccess: Boolean = false) {

    abstract val template: String

    abstract val description: String

    private fun help(commandModule: AbstractCommandModule, channel: MessageChannel) {
        val message = EmbedBuilder()
                .setAuthor(BotUtils.bot.selfUser.name, null, BotUtils.bot.selfUser.effectiveAvatarUrl)
                .setColor(BotUtils.PRIMARY_COLOR)
                .setTitle("Documentation for `$name`")
                .addField(
                        "Usage",
                        "`${BotUtils.BOT_PREFIX} "
                                + (if (commandModule != GeneralCommandModule) "${commandModule.shortName} " else "")
                                + "$name $template`",
                        true
                )
                .addField("Description", description, false)
        MessageUtils.sendMessage(channel, message.build())
    }

    fun preRunCommand(commandModule: AbstractCommandModule, event: MessageReceivedEvent, args: List<String>) {
        val hasAccess: Boolean = event.guild.idLong == BotUtils.SRID
        when {
            args.isNotEmpty() && args.last() == "--help" -> if (hasAccess) help(commandModule, event.channel)
            args.size < minArgs -> if (hasAccess) MessageUtils.sendMessage(
                    event.channel, "Invalid command, do you need help ? (see documentation with `--help`)")
            privateAccess -> if (hasAccess) runCommand(event, args)
            else -> runCommand(event, args)
        }
    }

    abstract fun runCommand(event: MessageReceivedEvent, args: List<String>)
}