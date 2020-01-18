package com.github.sylux6.watanabot.internal.commands

import com.github.sylux6.watanabot.commands.general.GeneralCommandModule
import com.github.sylux6.watanabot.commands.music.MusicCommandModule
import com.github.sylux6.watanabot.internal.exceptions.CommandAccessException
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.internal.types.BotMessageType
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.BotUtils
import com.github.sylux6.watanabot.utils.MessageUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractCommand(val name: String, private val minArgs: Int = 0,
                               val levelAccess: List<CommandLevelAccess> = listOf(CommandLevelAccess.ALL)) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

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
        try {
            for (access in levelAccess) {
                if (!checkCommandAccess(event, access)) {
                    throw CommandAccessException(access.info)
                }
            }
            when {
                args.contains("--help") -> help(commandModule, event.channel)
                args.size < minArgs -> throw CommandException("Invalid command, do you need help ? (see documentation with `--help`)")
                else -> runCommand(event, args)
            }
        } catch (e: CommandException) {
            MessageUtils.sendBotMessage(
                    event.channel,
                    e.message ?: "Error during ${commandModule.name} command",
                    BotMessageType.ERROR
            )
        } catch (e: Exception) {
            // Something unexpected happened
            logger.error(e.message)
        }
    }

    abstract fun runCommand(event: MessageReceivedEvent, args: List<String>): Any
}

fun checkCommandAccess(event: MessageReceivedEvent, access: CommandLevelAccess): Boolean {
    return when(access) {
        CommandLevelAccess.IN_VOICE -> event.member!!.voiceState!!.inVoiceChannel()
        CommandLevelAccess.IN_VOICE_WITH_BOT -> MusicCommandModule.isInBotVoiceChannel(event)
        CommandLevelAccess.BOT_IN_VOICE -> event.guild.audioManager.isConnected
        CommandLevelAccess.ADMIN -> event.member!!.isOwner
        CommandLevelAccess.OWNER -> event.author == BotUtils.bot.retrieveApplicationInfo().complete().owner
        CommandLevelAccess.PRIVATE -> event.guild.idLong == BotUtils.SRID
        else -> true
    }
}