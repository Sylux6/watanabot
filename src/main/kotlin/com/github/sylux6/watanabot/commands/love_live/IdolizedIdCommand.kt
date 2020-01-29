package com.github.sylux6.watanabot.commands.love_live

import com.github.sylux6.watanabot.commands.love_live.LoveLiveCommandModule.getCardByID
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.message.mentionAt
import com.github.sylux6.watanabot.utils.message.sendFile
import com.github.sylux6.watanabot.utils.message.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object IdolizedIdCommand : AbstractCommand("iid", 1) {
    override val template: String
        get() = "<id>"
    override val description: String
        get() = "Get idolized Love Live! School Idol Festival card by ID."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val c = getCardByID(Integer.valueOf(args.first()), event.author)

        if (c == null) {
            sendMessage(
                event.channel,
                mentionAt(event.author) + " Not found"
            )
            return
        }
        sendFile(
            event.channel,
            c.fileIdolizedImg,
            "idol.png",
            c.toEmbedMessage()
        )
    }
}