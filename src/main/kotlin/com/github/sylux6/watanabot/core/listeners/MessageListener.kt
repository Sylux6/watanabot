package com.github.sylux6.watanabot.core.listeners

import com.github.sylux6.watanabot.commands.general.GeneralCommandModule
import com.github.sylux6.watanabot.commands.picture.PictureCommandModule
import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULE_MAP
import com.github.sylux6.watanabot.core.CommandHandler.SERVICE
import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.internal.types.BotMessageType
import com.github.sylux6.watanabot.threads.ThreadCommand
import com.github.sylux6.watanabot.threads.ThreadGeneralBehaviour
import com.github.sylux6.watanabot.threads.ThreadMentionBehaviour
import com.github.sylux6.watanabot.utils.bot.BOT_PREFIX
import com.github.sylux6.watanabot.utils.message.sendBotMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {

        // Ignore messages from BOT
        if (event.message.author.isBot)
            return

        // Get all elements of the received message separated by spaces
        val args: MutableList<String> =
            mutableListOf(*event.message.contentDisplay.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

        // First ensure at least the command and prefix is present, the arg length can
        // be handled by your command func
        if (args.isEmpty())
            return

        // In those cases there is no command to execute
        if (!args.first().startsWith(BOT_PREFIX)) {
            if (event.message.mentionedUsers.contains(event.jda.selfUser))
                SERVICE.execute(ThreadMentionBehaviour(event))
            else
                SERVICE.execute(ThreadGeneralBehaviour(event))
            return
        }

        // Bot prefix is not needed anymore
        args.removeAt(0)
        // Nothing to do
        if (args.size == 0)
            return

        val commandModule: AbstractCommandModule
        // No specific module found
        if (COMMAND_MODULE_MAP.containsKey(args.first())) {
            commandModule = COMMAND_MODULE_MAP[args.first()]!!
            // Module name is not needed anymore
            args.removeAt(0)
        } else {
            commandModule = GeneralCommandModule
        }

//        if (module is MusicModule) { // Block MusicModule com.github.sylux6.watanabot.commands if Blindtest is running
//            val blindtestModule = CommandHandler.moduleMap["blindtest"] as BlindtestModule
//            if (blindtestModule.getBlindtestInstance(event.guild) != null) {
//                MessageUtils.sendMessage(event.channel, "A blindtest game is running")
//                return
//            }
//        }

        // There is no command
        if (args.isEmpty()) {
            return
        }
        // Calling the command
        if (commandModule.commandMap.containsKey(args.first())) {
            SERVICE.execute(
                ThreadCommand(commandModule, commandModule.commandMap[args.first()]!!, event, args.drop(1))
            )
        }
        // Picture module: user doesn't need to give a command to get an image
        else if (commandModule is PictureCommandModule) {
            SERVICE.execute {
                try {
                    PictureCommandModule.getImage(event, args)
                } catch (e: CommandException) {
                    sendBotMessage(
                        event.channel,
                        e.message ?: "Error during ${commandModule.name} command",
                        BotMessageType.ERROR
                    )
                }
            }
        }
    }
}
