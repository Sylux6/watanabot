package com.github.sylux6.watanabot.utils

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

    fun buildBotMessage(message: String): MessageEmbed {
        return EmbedBuilder()
                .setAuthor(BotUtils.bot.selfUser.name, null, BotUtils.bot.selfUser.effectiveAvatarUrl)
                .setColor(BotUtils.PRIMARY_COLOR)
                .setDescription(message)
                .build()
    }

}
