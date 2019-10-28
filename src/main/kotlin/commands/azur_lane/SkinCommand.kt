package commands.azur_lane

import commands.azur_lane.entities.Ship.Companion.getShipByName
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils.sendMessage

object SkinCommand : AbstractCommand("skin", 2) {
    override val template: String
        get() = "<number> <name>"
    override val description: String
        get() = "Show skin of a ship. Use `skins` command to get the available skins."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        try {
            val index = args.first().toInt()
            val shipName = args.drop(1).joinToString(" ")
            val ship = getShipByName(shipName)
            if (ship == null) {
                sendMessage(event.channel, "Not found")
                return
            }
            sendMessage(event.channel, ship.skinEmbed(index))
        } catch (e: Exception) {
            sendMessage(event.channel, "Internal error, please retry")
        }
    }
}