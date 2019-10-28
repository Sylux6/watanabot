package commands.love_live

import commands.love_live.LoveLiveCommandModule.CARD_ID_API_URL
import commands.love_live.LoveLiveCommandModule.getCardByID
import commands.love_live.entities.Card
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.json.JSONArray
import utils.BotUtils.random
import utils.HttpRequest
import utils.JsonUtils
import utils.MessageUtils.mentionAt
import utils.MessageUtils.sendFile
import utils.MessageUtils.sendMessage

object ScoutCommand : AbstractCommand("scout", 1) {
    override val template: String
        get() = "(u's | aqours)"
    override val description: String
        get() = "Scout an idol from a given unit."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        random.setSeed(System.currentTimeMillis())
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
        val roll: Int = random.nextInt(100) + 1

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

        jsonArray = JsonUtils.stringToJsonArray(
                HttpRequest.getRequest(CARD_ID_API_URL, "idol_main_unit=$unit", "rarity=$rarity"))
        c = getCardByID(jsonArray.getInt(random.nextInt(jsonArray.length())), event.author)

        if (c == null) {
            sendMessage(event.channel, "${mentionAt(event.author)} Not found")
            return
        }
        sendFile(event.channel, c.fileImg, "idol.png", c.toEmbedMessage())
    }
}