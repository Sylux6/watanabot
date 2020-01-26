package com.github.sylux6.watanabot.commands.azur_lane

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.exceptions.ShipNotFoundException
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object SkinCommand : AbstractCommand("skin", 2) {
    override val template: String
        get() = "<number> <name>"
    override val description: String
        get() = "Show skin of a ship. Use `skins` command to get the available skins."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        try {
            val ship = Atago.getShipByName(args.drop(1).joinToString(" "))
            sendMessage(event.channel, AzurLaneCommandModule.skinEmbed(ship, args.first().toInt()))
        } catch (e: ShipNotFoundException) {
            throw CommandException(e.message)
        }
    }
}