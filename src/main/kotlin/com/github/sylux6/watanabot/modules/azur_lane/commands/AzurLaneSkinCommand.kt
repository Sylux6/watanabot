package com.github.sylux6.watanabot.modules.azur_lane.commands

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.azurapi.azurapikotlin.internal.exceptions.ShipNotFoundException
import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.modules.azur_lane.utils.skinEmbed
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object AzurLaneSkinCommand : AbstractCommand("skin", 2) {
    override val template: String
        get() = "<number> <ship_name>"
    override val description: String
        get() = "Show skin of a ship. Use `skins` command to get the available skins."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        try {
            val ship = Atago.getShipByName(args.drop(1).joinToString(" "))
            sendMessage(
                event.channel,
                skinEmbed(
                    ship,
                    args.first().toInt()
                )
            )
        } catch (e: ShipNotFoundException) {
            commandFail(e.message)
        }
    }
}
