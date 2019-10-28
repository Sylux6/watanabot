package commands.love_live

import commands.love_live.LoveLiveCommandModule.getCardByID
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.mentionAt
import utils.MessageUtils.sendFile
import utils.MessageUtils.sendMessage

object IdolizedIdCommand : AbstractCommand("iid", 1) {
    override val template: String
        get() = "<id>"
    override val description: String
        get() = "Get idolized Love Live! School Idol Festival card by ID."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val c = getCardByID(Integer.valueOf(args.first()), event.author)

        if (c == null) {
            sendMessage(event.channel, mentionAt(event.author) + " Not found")
            return
        }
        sendFile(event.channel, c.fileIdolizedImg, "idol.png", c.toEmbedMessage())
    }
}