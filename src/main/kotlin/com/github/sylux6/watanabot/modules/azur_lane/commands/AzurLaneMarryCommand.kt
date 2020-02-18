package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.sendMessageAt
import db.models.Users
import db.utils.insertOrUpdate
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.transactions.transaction

object AzurLaneMarryCommand : AbstractCommand("marry", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Marry to a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Ship {
        val ship = Atago.getShipByName(args.joinToString(" "))
        transaction {
            Users
                .insertOrUpdate(Users.userId) {
                    it[userId] = event.author.idLong
                    it[azurLaneWaifuId] = ship.id
                }
        }
        sendMessageAt(event.channel, event.author, "is now pledged to ${ship.names.en} \uD83D\uDC8D")
        return ship
    }
}
