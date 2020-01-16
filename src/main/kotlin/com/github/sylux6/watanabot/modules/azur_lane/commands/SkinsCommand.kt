package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.exceptions.ShipNotFoundException
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.modules.azur_lane.AzurLaneCommandModule
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object SkinsCommand : AbstractCommand("skins", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get available skins of a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        try {
            val ship = Atago.getShipByName(args.joinToString(" "))
            sendMessage(
                event.channel,
                AzurLaneCommandModule.skinListEmbed(
                    ship
                )
            )
        } catch (e: ShipNotFoundException) {
            throw CommandException(e.message)
        }
    }
}