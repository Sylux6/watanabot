package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.sendBotMessage
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Users
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object AzurLanePopularityCommand : AbstractCommand("popularity") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get the top waifus of the server."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val topThree: List<Pair<String, Int>> = transaction {
            Users
                .slice(Users.azurLaneWaifuId, Users.userId)
                .select { Users.azurLaneWaifuId.isNotNull() }
                .filter { event.guild.getMemberById(it[Users.userId]) != null }
                .groupBy { it[Users.azurLaneWaifuId]!! }
                .mapValues { (_, users) -> users.size }
                .entries.sortedByDescending { it.value }
                .map { Pair(it.key, it.value) }
                .take(3)
        }
        if (topThree.isNotEmpty()) {
            mostPopularShipEmbed(event.channel, topThree)
        } else {
            sendBotMessage(event.channel, message = "Nobody has pledged to a ship yet")
        }
    }

    private fun mostPopularShipEmbed(channel: MessageChannel, topShips: List<Pair<String, Int>>) {
        val message = EmbedBuilder()
            .setTitle("Most popular ships")
            .setColor(BOT_PRIMARY_COLOR)
            .setAuthor(jda.selfUser.name, null, jda.selfUser.effectiveAvatarUrl)
        for ((index, pair) in topShips.withIndex()) {
            val (shipId: String, _) = pair
            val ship = Atago.getShipById(shipId)
            when (index) {
                0 -> {
                    message.setThumbnail(ship.thumbnail)
                    message.addField("", "\uD83E\uDD47 ${ship.names.en}", false)
                }
                1 -> message.addField("", "\uD83E\uDD48 ${ship.names.en}", false)
                2 -> message.addField("", "\uD83E\uDD49 ${ship.names.en}", false)
            }
        }
        sendMessage(channel, message.build())
    }
}
