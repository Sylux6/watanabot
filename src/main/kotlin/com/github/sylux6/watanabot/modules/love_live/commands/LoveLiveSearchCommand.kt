package com.github.sylux6.watanabot.modules.love_live.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.modules.love_live.LoveLiveCommandModule.CARD_ID_API_URL
import com.github.sylux6.watanabot.modules.love_live.utils.getCardByID
import com.github.sylux6.watanabot.utils.getRequest
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.sendFile
import com.github.sylux6.watanabot.utils.sendMessage
import com.github.sylux6.watanabot.utils.stringToJsonArray
import kotlin.random.Random
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import okhttp3.OkHttpClient

object LoveLiveSearchCommand : AbstractCommand("search", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get a random Love Live! School Idol Festival card of an idol"

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {

        val client = OkHttpClient()
        val jsonArray =
            stringToJsonArray(getRequest(client, CARD_ID_API_URL, "search=${args.joinToString(" ")}"))
        if (jsonArray.isEmpty) {
            sendMessage(event.channel, "No results")
            return
        }
        val c = getCardByID(jsonArray.getInt(Random.nextInt(jsonArray.length())), event.author)
        if (c == null) {
            sendMessage(event.channel, "${mentionAt(event.author)} Not found")
            return
        }
        sendFile(event.channel, c.fileImg, "idol.png", c.toEmbedMessage())
    }
}
