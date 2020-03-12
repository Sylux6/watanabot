package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.jdaInstance
import com.github.sylux6.watanabot.utils.sendBotMessage
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.AzurLaneUsers
import java.util.Locale
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object AzurLanePopularityCommand : AbstractCommand("popularity") {
    override val template: String
        get() = "[<ship_name>]"
    override val description: String
        get() = "Get popularity stats of a ship or get the top 3."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            // Top most popular ships
            val topThree: List<Pair<String, List<Long>>> = transaction {
                AzurLaneUsers
                    .slice(AzurLaneUsers.oathId, AzurLaneUsers.userId)
                    .select { AzurLaneUsers.oathId.isNotNull() }
                    .filter { event.guild.getMemberById(it[AzurLaneUsers.userId]) != null }
                    .groupBy { it[AzurLaneUsers.oathId]!! }
                    .mapValues { (_, users) -> users.map { it[AzurLaneUsers.userId] } }
                    .entries.sortedByDescending { map -> map.value.size }
                    .map { it.key to it.value }
            }
            if (topThree.isNotEmpty()) {
                sendMessage(
                    event.channel, mostPopularShipEmbed(topThree)
                        .setFooter("${event.guild.name} stats", event.guild.iconUrl)
                        .build()
                )
            } else {
                sendBotMessage(event.channel, message = "Nobody has pledged to a ship yet")
            }
        } else {
            // Popularity for one ship
            val ship: Ship = Atago.getShipByName(args.joinToString(" "))
            val membersByShipId: Map<String, List<Pair<DateTime, List<Member>>>> = transaction {
                AzurLaneUsers
                    .slice(AzurLaneUsers.userId, AzurLaneUsers.oathId, AzurLaneUsers.oathDate)
                    .select { AzurLaneUsers.oathId.isNotNull() }
                    .groupBy { it[AzurLaneUsers.oathId]!! }
                    .mapValues { (_, value) ->
                        value
                            .groupBy { it[AzurLaneUsers.oathDate] }
                            .entries.sortedBy { map -> map.key }
                            .map { it.key!! to it.value.mapNotNull { user -> event.guild.getMemberById(user[AzurLaneUsers.userId]) } }
                    }
            }
            if (membersByShipId.containsKey(ship.id)) {
                sendMessage(event.channel, shipPopularity(ship, membersByShipId)
                    .setFooter("${event.guild.name} stats", event.guild.iconUrl)
                    .build()
                )
            } else {
                sendBotMessage(event.channel, message = "Nobody has pledged to ${ship.names.en} yet", thumbnail = ship.thumbnail)
            }
        }
    }

    private fun mostPopularShipEmbed(topShips: List<Pair<String, List<Long>>>): EmbedBuilder {
        val message = EmbedBuilder()
            .setTitle("Most popular ships")
            .setColor(BOT_PRIMARY_COLOR)
            .setAuthor(jdaInstance.selfUser.name, null, jdaInstance.selfUser.effectiveAvatarUrl)
        val total: Int = topShips.map { (_, userList) -> userList.size }.reduce { acc, i -> acc + i }
        for ((index, pair) in topShips.take(3).withIndex()) {
            val (shipId: String, userList: List<Long>) = pair
            val ship = Atago.getShipById(shipId)
            when (index) {
                0 -> {
                    message.setThumbnail(ship.thumbnail)
                    message.addField(
                        "(**${userList.size}**) | **${String.format(Locale.ENGLISH, " %.1f", userList.size.toDouble() / total * 100)}%**",
                        "\uD83E\uDD47 **${ship.names.en}**",
                        false
                    )
                }
                1 -> message.addField(
                    "(**${userList.size}**) | **${String.format(Locale.ENGLISH, " %.1f", userList.size.toDouble() / total * 100)}%**",
                    "\uD83E\uDD48 **${ship.names.en}**",
                    false
                )
                2 -> message.addField(
                    "(**${userList.size}**) | **${String.format(Locale.ENGLISH, " %.1f", userList.size.toDouble() / total * 100)}%**",
                    "\uD83E\uDD49 **${ship.names.en}**",
                    false
                )
            }
        }
        return message
    }

    private fun shipPopularity(ship: Ship, membersByShipId: Map<String, List<Pair<DateTime, List<Member>>>>): EmbedBuilder {
        val total: Int = membersByShipId
            .flatMap { (_, pairList) -> pairList
                .map { (_, memberList) -> memberList.size } }
            .reduce { acc, i -> acc + i }
        val shipTotal: Int = (membersByShipId[ship.id] ?: error(""))
            .map { (_, memberList) -> memberList.size }
            .reduce { acc, i -> acc + i }
        val message = EmbedBuilder()
            .setTitle(ship.names.en + " (" + ship.names.jp + ")", ship.wikiUrl)
            .setDescription("(**$shipTotal**) | **${String.format(Locale.ENGLISH, "%.1f", shipTotal.toDouble() / total * 100)}%**")
            .setColor(BOT_PRIMARY_COLOR)
            .setAuthor(jdaInstance.selfUser.name, null, jdaInstance.selfUser.effectiveAvatarUrl)
            .setThumbnail(ship.thumbnail)
        for (pair in membersByShipId[ship.id] ?: error("")) {
            val (date: DateTime, members: List<Member>) = pair
                message.addField(
                    date.toString("dd-MM-yyyy"),
                    members.joinToString("\n") { it.effectiveName },
                    false
                )
        }
        return message
    }
}
