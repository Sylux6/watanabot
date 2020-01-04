package com.github.sylux6.watanabot.commands.love_live

import com.github.sylux6.watanabot.commands.love_live.LoveLiveCommandModule.getCardByID
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.MessageUtils.mentionAt
import com.github.sylux6.watanabot.utils.MessageUtils.sendFile
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage

object IdCommand : AbstractCommand("id", 1) {
    override val template: String
        get() = "<id>"
    override val description: String
        get() = "Get Love Live! School Idol Festival card by ID."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val c = getCardByID(Integer.valueOf(args.first()), event.author)

        if (c == null) {
            sendMessage(event.channel, "${mentionAt(event.author)} Not found")
            return
        }
        sendFile(event.channel, c.fileImg, "idol.png", c.toEmbedMessage())
    }
}