package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.findMemberOrNull
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Users
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

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
        val shipId: String = transaction {
            Users
                .slice(Users.azurLaneWaifuId)
                .select { Users.userId eq member.idLong }
                .singleOrNull()?.get(Users.azurLaneWaifuId)
                ?: commandFail("**${member.effectiveName}** didn't pledge to a ship")
        }
        val ship: Ship = Atago.getShipById(shipId)
        waifuEmbedMessage(event.channel, member, Atago.getShipById(shipId))
        return ship
    }

    private fun waifuEmbedMessage(channel: MessageChannel, member: Member, ship: Ship) {
        val message = EmbedBuilder()
            .setColor(BOT_PRIMARY_COLOR)
            .setAuthor(member.effectiveName, null, member.user.effectiveAvatarUrl)
            .setThumbnail(ship.thumbnail)
            .setDescription("\uD83D\uDC8D ${ship.names.en}")
        sendMessage(channel, message.build())
    }
}
