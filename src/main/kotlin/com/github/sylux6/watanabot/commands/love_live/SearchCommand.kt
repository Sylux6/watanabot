package com.github.sylux6.watanabot.commands.love_live

import com.github.sylux6.watanabot.commands.love_live.LoveLiveCommandModule.CARD_ID_API_URL
import com.github.sylux6.watanabot.commands.love_live.LoveLiveCommandModule.getCardByID
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.HttpRequest
import com.github.sylux6.watanabot.utils.JsonUtils
import com.github.sylux6.watanabot.utils.MessageUtils
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import kotlin.random.Random

object SearchCommand : AbstractCommand("search", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get a random Love Live! School Idol Festival card of an idol"

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {

        val jsonArray =
            JsonUtils.stringToJsonArray(HttpRequest.getRequest(CARD_ID_API_URL, "search=${args.joinToString(" ")}"))
        if (jsonArray.isEmpty) {
            MessageUtils.sendMessage(event.channel, "No results")
            return
        }
        val c = getCardByID(jsonArray.getInt(Random.nextInt(jsonArray.length())), event.author)
        if (c == null) {
            MessageUtils.sendMessage(event.channel, "${MessageUtils.mentionAt(event.author)} Not found")
            return
        }
        MessageUtils.sendFile(event.channel, c.fileImg, "idol.png", c.toEmbedMessage())
    }
}