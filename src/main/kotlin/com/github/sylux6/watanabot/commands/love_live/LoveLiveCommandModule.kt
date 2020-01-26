package com.github.sylux6.watanabot.commands.love_live

import com.github.sylux6.watanabot.commands.love_live.entities.Card
import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.utils.getRequest
import com.github.sylux6.watanabot.utils.stringToJsonObject
import net.dv8tion.jda.api.entities.User
import okhttp3.OkHttpClient
import java.io.IOException

object LoveLiveCommandModule : AbstractCommandModule(
    "Love Live!",
    "ll",
    setOf(InfoCommand, ScoutCommand, IdCommand, IdolizedIdCommand, SearchCommand)
) {
    override val moduleDescription: String
        get() = "Commands related to Love Live!"

    const val CARD_API_URL = "https://schoolido.lu/api/cards/"
    const val CARD_ID_API_URL = "http://schoolido.lu/api/cardids/"

    fun getCardByID(id: Int, user: User): Card? {
        return try {
            val client = OkHttpClient()
            val json = stringToJsonObject(getRequest(client, "$CARD_API_URL/${Integer.valueOf(id)}"))
            if (json.has("detail")) null else Card(json, user)
        } catch (e: IOException) {
            null
        }
    }
}