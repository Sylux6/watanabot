package com.github.sylux6.watanabot.utils

import com.github.sylux6.watanabot.internal.types.BotMessageType
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

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

fun buildBotMessage(
    title: String? = null,
    message: String,
    type: BotMessageType = BotMessageType.NORMAL,
    thumbnail: String? = null
): MessageEmbed {
    val embed = EmbedBuilder()
    when (type) {
        BotMessageType.NORMAL -> embed.setAuthor(title ?: jdaInstance.selfUser.name, null, jdaInstance.selfUser.effectiveAvatarUrl)
        BotMessageType.FATAL -> {
            embed.setAuthor(title ?: type.display)
            embed.setTitle("report issue", "https://github.com/Sylux6/WatanaBot/issues")
        }
        else -> embed.setAuthor(title ?: type.display)
    }
    if (thumbnail != null) {
        embed.setThumbnail(thumbnail)
    }
    return embed
        .setColor(type.color)
        .setDescription(message)
        .build()
}
