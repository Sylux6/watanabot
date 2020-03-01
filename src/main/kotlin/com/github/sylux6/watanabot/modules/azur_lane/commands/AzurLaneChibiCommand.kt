package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.entities.Ship
import com.github.azurapi.azurapikotlin.internal.exceptions.ShipNotFoundException
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.modules.azur_lane.utils.chibiEmbed
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object AzurLaneChibiCommand : AbstractCommand("chibi", 1) {
    override val template: String
        get() = "<ship_name>"
    override val description: String
        get() = "Show chibi of a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>): Ship {
        try {
            val ship = Atago.getShipByName(args.joinToString(" "))
            sendMessage(
                event.channel,
                chibiEmbed(ship)
            )
            return ship
        } catch (e: ShipNotFoundException) {
            commandFail(e.message)
        }
    }
}
