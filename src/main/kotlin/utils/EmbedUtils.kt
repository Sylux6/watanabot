package utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

object EmbedUtils {

    fun buildEmbedImageBooru(title: String, url: String, tags: List<String>, imgURL: String): MessageEmbed {
        val embedMessage = EmbedBuilder()
        embedMessage.setTitle(title, url)
        embedMessage.setDescription(tags.joinToString(" "))
        embedMessage.setImage(imgURL)
        return embedMessage.build()
    }

    fun buildEmbedImageOnly(imgURL: String): MessageEmbed {
        val embedMessage = EmbedBuilder()
        embedMessage.setImage(imgURL)
        return embedMessage.build()
    }

    fun buildEmbedImageOnly(title: String, url: String, imgURL: String): MessageEmbed {
        val embedMessage = EmbedBuilder()
        embedMessage.setTitle(title, url)
        embedMessage.setImage(imgURL)
        return embedMessage.build()
    }

    fun buildEmbedImageOnly(title: String, imgURL: String): MessageEmbed {
        val embedMessage = EmbedBuilder()
        embedMessage.setTitle(title)
        embedMessage.setImage(imgURL)
        return embedMessage.build()
    }

}
