package com.github.sylux6.watanabot.utils

import com.github.sylux6.watanabot.internal.types.BotMessageType
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

object EmbedUtils {

    fun buildEmbedImageBooru(url: String, title: String, artist: String, imgURL: String): MessageEmbed {
        val embedMessage = EmbedBuilder()
            .setTitle(title, url)
            .setAuthor(artist)
            .setImage(imgURL)
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

    fun buildBotMessage(message: String, type: BotMessageType = BotMessageType.NORMAL): MessageEmbed {
        val embed = EmbedBuilder()
        if (type == BotMessageType.NORMAL) {
            embed.setAuthor(BotUtils.bot.selfUser.name, null, BotUtils.bot.selfUser.effectiveAvatarUrl)
        } else {
            embed.setAuthor(type.display)
        }
        return embed
            .setColor(type.color)
            .setDescription(message)
            .build()
    }
}
