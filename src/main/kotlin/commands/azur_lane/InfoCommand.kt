package commands.azur_lane

import commands.azur_lane.entities.Ship
import commands.azur_lane.entities.Ship.Companion.getShipByName
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object InfoCommand : AbstractCommand("info", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get details about a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        try {
            val shipName = args.joinToString(" ")
            val ship: Ship? = getShipByName(shipName)
            if (ship == null) {
                sendMessage(event.channel, "Not found")
                return
            }
            sendMessage(event.channel, ship.toEmbed())
        } catch (e: Exception) {
            sendMessage(event.channel, "Internal error, please retry");
        }
    }
}