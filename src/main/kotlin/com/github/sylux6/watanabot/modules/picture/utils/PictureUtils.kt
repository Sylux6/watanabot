package com.github.sylux6.watanabot.modules.picture.utils

import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.utils.buildEmbedImageBooru
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.kodehawa.lib.imageboards.DefaultImageBoards
import net.kodehawa.lib.imageboards.entities.Rating

fun getImage(event: MessageReceivedEvent, search: List<String>, rating: Rating = Rating.SAFE) {
    try {
        val images = DefaultImageBoards.DANBOORU.search(search.joinToString(" "), rating).blocking()
        // The last rating tag is taken during search process so user can not trick the nsfw filter
        when {
            images.isNotEmpty() -> {
                val image = images.random()
                sendMessage(
                    event.channel,
                    buildEmbedImageBooru(
                        image.url,
                        image.tag_string_character,
                        image.tag_string_artist,
                        image.url
                    )
                )
            }
            rating == Rating.EXPLICIT -> // If no explicit image has been found, try again with questionable rating
                getImage(event, search, Rating.QUESTIONABLE)
            rating == Rating.QUESTIONABLE -> // If no questionable image has been found, try again with safe rating
                getImage(event, search, Rating.SAFE)
            else -> commandFail("No picture found")
        }
    } catch (e: Exception) {
        commandFail(e.message)
    }
}
