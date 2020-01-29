package com.github.sylux6.watanabot.utils.message

import com.github.sylux6.watanabot.internal.types.BotMessageType
import com.github.sylux6.watanabot.utils.bot.bot
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

fun buildBotMessage(message: String, type: BotMessageType = BotMessageType.NORMAL): MessageEmbed {
    val embed = EmbedBuilder()
    if (type == BotMessageType.NORMAL) {
        embed.setAuthor(bot.selfUser.name, null, bot.selfUser.effectiveAvatarUrl)
    } else {
        embed.setAuthor(type.display)
    }
    return embed
        .setColor(type.color)
        .setDescription(message)
        .build()
}