package com.github.sylux6.watanabot.commands.azur_lane

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.azurapi.azurapikotlin.internal.exceptions.ShipNotFoundException
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object InfoCommand : AbstractCommand("info", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get details about a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Ship {
        try {
            val ship = Atago.getShipByName(args.joinToString(" "))
            sendMessage(event.channel, AzurLaneCommandModule.shipToEmbed(ship))
            return ship
        } catch (e: ShipNotFoundException) {
            throw CommandException(e.message)
        }
    }
}