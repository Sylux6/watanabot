package core.listeners

import commands.general.GeneralCommandModule
import commands.picture.PictureCommandModule
import core.CommandHandler.COMMAND_MODULE_MAP
import core.CommandHandler.SERVICE
import internal.commands.AbstractCommandModule
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import threads.ThreadCommand
import threads.ThreadGeneralBehaviour
import threads.ThreadMentionBehaviour
import utils.BotUtils.BOT_PREFIX

class MessageListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {

        // Ignore messages from BOT
        if (event.message.author.isBot)
            return

        // Get all elements of the received message separated by spaces
        val args: MutableList<String> = mutableListOf(*event.message.contentDisplay.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

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

//        if (module is MusicModule) { // Block MusicModule commands if Blindtest is running
//            val blindtestModule = CommandHandler.moduleMap["blindtest"] as BlindtestModule
//            if (blindtestModule.getBlindtestInstance(event.guild) != null) {
//                MessageUtils.sendMessage(event.channel, "A blindtest game is running")
//                return
//            }
//        }

        // Calling the command
        if (commandModule.commandMap.containsKey(args.first())) {
            SERVICE.execute(
                    ThreadCommand(commandModule, commandModule.commandMap[args.first()]!!, event, args.drop(1))
            )
        }
        else if (commandModule is PictureCommandModule) { // Getting image by default if unknown command
            SERVICE.execute { PictureCommandModule.getImage(event, args) }
//            SERVICE.execute(ThreadPictureDefaultBehaviour(event, argTab))
        }
    }

}
