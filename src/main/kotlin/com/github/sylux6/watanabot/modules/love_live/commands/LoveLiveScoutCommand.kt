package com.github.sylux6.watanabot.modules.love_live.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.modules.love_live.LoveLiveCommandModule.CARD_ID_API_URL
import com.github.sylux6.watanabot.modules.love_live.LoveLiveCommandModule.getCardByID
import com.github.sylux6.watanabot.modules.love_live.entities.Card
import com.github.sylux6.watanabot.utils.getRequest
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.sendFile
import com.github.sylux6.watanabot.utils.sendMessage
import com.github.sylux6.watanabot.utils.stringToJsonArray
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import okhttp3.OkHttpClient
import org.json.JSONArray
import kotlin.random.Random

object LoveLiveScoutCommand : AbstractCommand("scout", 1) {
    override val template: String
        get() = "(u's | aqours)"
    override val description: String
        get() = "Scout an idol from a given unit."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val c: Card?
        val jsonArray: JSONArray
        val unit: String = when (args.first()) {
            "u's" -> "µ's"
            "aqours" -> "aqours"
            else -> {
                sendMessage(event.channel, "I don't know this unit")
                return
            }
        }
        val rarity: String
        val roll: Int = Random.nextInt(100) + 1

        // Main unit scouting
        // RNG
        rarity = when {
            // UR = 1%
            roll == 1 -> "UR"
            // SSR = 4%
            roll <= 5 -> "SSR"
            // SR = 15%
            roll <= 20 -> "SR"
            // R = 80%
            else -> "R"
        }

        val client = OkHttpClient()
        jsonArray = stringToJsonArray(
            getRequest(client, CARD_ID_API_URL, "idol_main_unit=$unit", "rarity=$rarity")
        )
        c = getCardByID(jsonArray.getInt(Random.nextInt(jsonArray.length())), event.author)

        if (c == null) {
            sendMessage(event.channel, "${mentionAt(event.author)} Not found")
            return
        }
        sendFile(event.channel, c.fileImg, "idol.png", c.toEmbedMessage())
    }
}