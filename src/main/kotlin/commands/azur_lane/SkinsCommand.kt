package commands.azur_lane

import commands.azur_lane.entities.Ship.Companion.getShipByName
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object SkinsCommand : AbstractCommand("skins", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get available skins of a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val shipName = args.joinToString(" ")
        try {
            val ship = getShipByName(shipName)
            if (ship == null) {
                sendMessage(event.channel, "Not found")
                return
            }
            sendMessage(event.channel, ship.skinListEmbed())
        } catch (e: Exception) {
            sendMessage(event.channel, "Internal error, please retry")
        }
    }
}