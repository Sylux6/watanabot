package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.sendMessageAt
import db.models.AzurLaneUsers
import db.utils.insertOrUpdate
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object AzurLaneMarryCommand : AbstractCommand("marry", 1) {
    override val template: String
        get() = "<ship_ame>"
    override val description: String
        get() = "Marry to a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Ship {
        val ship = Atago.getShipByName(args.joinToString(" "))
        transaction {
            AzurLaneUsers
                .insertOrUpdate(AzurLaneUsers.userId) {
                    it[userId] = event.author.idLong
                    it[oathId] = ship.id
                    it[oathDate] = DateTime.now()
                }
        }
        sendMessageAt(event.channel, event.author, "is now pledged to ${ship.names.en} \uD83D\uDC8D")
        return ship
    }
}
