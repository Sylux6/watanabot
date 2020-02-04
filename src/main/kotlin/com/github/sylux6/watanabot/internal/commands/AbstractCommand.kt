package com.github.sylux6.watanabot.internal.commands

import com.github.sylux6.watanabot.internal.exceptions.CommandAccessException
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.internal.types.BotMessageType
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.modules.general.GeneralCommandModule
import com.github.sylux6.watanabot.modules.music.MusicCommandModule
import com.github.sylux6.watanabot.utils.PREFIX
import com.github.sylux6.watanabot.utils.PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.PRIVATE_SERVER_ID
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.sendBotMessage
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractCommand(
    val name: String,
    private val minArgs: Int = 0,
    val levelAccess: List<CommandLevelAccess> = listOf(CommandLevelAccess.EVERYONE)
) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    abstract val template: String

    abstract val description: String

    private fun help(commandModule: AbstractCommandModule, channel: MessageChannel) {
        val message = EmbedBuilder()
            .setAuthor(jda.selfUser.name, null, jda.selfUser.effectiveAvatarUrl)
            .setColor(PRIMARY_COLOR)
            .setTitle("Documentation for `$name`")
            .addField(
                "Usage",
                "`${PREFIX} "
                    + (if (commandModule != GeneralCommandModule) "${commandModule.shortName} " else "")
                    + "$name $template`",
                true
            )
            .addField("Description", description, false)
        sendMessage(channel, message.build())
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
            sendBotMessage(
                event.channel,
                message = e.message ?: "Error during ${commandModule.name} command",
                type = BotMessageType.ERROR
            )
        } catch (e: Exception) {
            // Something unexpected happened
            logger.error(e.message)
            sendBotMessage(
                event.channel,
                message = "$e",
                type = BotMessageType.ERROR
            )
        }
    }

    abstract fun runCommand(event: MessageReceivedEvent, args: List<String>): Any
}

fun checkCommandAccess(event: MessageReceivedEvent, access: CommandLevelAccess): Boolean {
    return when (access) {
        CommandLevelAccess.IN_VOICE -> event.member!!.voiceState!!.inVoiceChannel()
        CommandLevelAccess.IN_VOICE_WITH_BOT -> MusicCommandModule.isInBotVoiceChannel(event)
        CommandLevelAccess.BOT_IN_VOICE -> event.guild.audioManager.isConnected
        CommandLevelAccess.ADMIN -> event.member!!.isOwner
        CommandLevelAccess.OWNER -> event.author == jda.retrieveApplicationInfo().complete().owner
        CommandLevelAccess.PRIVATE -> event.guild.idLong == PRIVATE_SERVER_ID
        else -> true
    }
}