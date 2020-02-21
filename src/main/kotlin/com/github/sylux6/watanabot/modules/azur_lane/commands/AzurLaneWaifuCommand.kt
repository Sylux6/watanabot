package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.findMemberOrNull
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.AzurLaneUsers
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object AzurLaneWaifuCommand : AbstractCommand("waifu") {
    override val template: String
        get() = "[<member>]"
    override val description: String
        get() = "Get the waifu of a member."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Ship {
        val member: Member = if (args.isEmpty()) {
            event.member!!
        } else {
            findMemberOrNull(event.guild, args.joinToString(" "))
                ?: commandFail("Cannot find user in the server")
        }
        val (shipId: String, date: DateTime) = transaction {
            AzurLaneUsers
                .slice(AzurLaneUsers.oathId, AzurLaneUsers.oathDate)
                .select { AzurLaneUsers.userId eq member.idLong and (AzurLaneUsers.oathId.isNotNull()) }
                .singleOrNull()?.let { it[AzurLaneUsers.oathId]!! to it[AzurLaneUsers.oathDate]!! }
                ?: commandFail("**${member.effectiveName}** has no shipfu")
        }
        val ship: Ship = Atago.getShipById(shipId)
        waifuEmbedMessage(event.channel, member, Atago.getShipById(shipId), date)
        return ship
    }

    private fun waifuEmbedMessage(channel: MessageChannel, member: Member, ship: Ship, date: DateTime) {
        val message = EmbedBuilder()
            .setColor(BOT_PRIMARY_COLOR)
            .setAuthor(member.effectiveName, null, member.user.effectiveAvatarUrl)
            .setThumbnail(ship.thumbnail)
            .addField(date.toString("dd-MM-yyyy"), "\uD83D\uDC8D ${ship.names.en}", false)
        sendMessage(channel, message.build())
    }
}
