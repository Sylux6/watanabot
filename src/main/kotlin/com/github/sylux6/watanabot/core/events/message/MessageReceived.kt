package com.github.sylux6.watanabot.core.events.message

import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULE_MAP
import com.github.sylux6.watanabot.core.reactions.botMention
import com.github.sylux6.watanabot.core.reactions.botReaction
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.internal.types.BotMessageType
import com.github.sylux6.watanabot.modules.general.GeneralCommandModule
import com.github.sylux6.watanabot.modules.picture.PictureCommandModule
import com.github.sylux6.watanabot.modules.picture.PictureCommandModule.getImage
import com.github.sylux6.watanabot.utils.BOT_PREFIX
import com.github.sylux6.watanabot.utils.sendBotMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

fun onMessageReceivedEvent(event: MessageReceivedEvent) {
    val args: List<String> = event.message.contentRaw.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
    if (args.isEmpty()) {
        return
    }

    // No bot prefix
    if (!args.first().startsWith(BOT_PREFIX)) {
        if (event.message.mentionedUsers.contains(event.jda.selfUser)) {
            botMention(event)
        } else {
            botReaction(event)
        }
        return
    }

    // At this point, words list looks like ["o7", "my_module", "my_command", ...]
    when (val selectedModule = COMMAND_MODULE_MAP.getOrDefault(args.getOrElse(1) { return }, GeneralCommandModule)) {
        GeneralCommandModule -> selectedModule.commandMap[args[1]]?.preRunCommand(selectedModule, event, args.drop(2))
            ?: return
        PictureCommandModule -> selectedModule.commandMap[args.getOrNull(2)]?.preRunCommand(
            selectedModule,
            event,
            args.drop(3)
        )
            ?: try {
                getImage(event, args.drop(3))
            } catch (e: CommandException) {
                sendBotMessage(
                    event.channel,
                    message = e.message ?: "Error during ${selectedModule.name} command",
                    type = BotMessageType.ERROR
                )
            }
        else -> selectedModule.commandMap[args.getOrNull(2)]?.preRunCommand(selectedModule, event, args.drop(3))
            ?: return
    }
}
