package commands.azur_lane

import com.github.azurapi.azurapikotlin.internal.exceptions.ApiException
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils
import utils.MessageUtils.sendMessage

object SkinCommand : AbstractCommand("skin", 2) {
    override val template: String
        get() = "<number> <name>"
    override val description: String
        get() = "Show skin of a ship. Use `skins` command to get the available skins."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        try {
            val ship = BotUtils.azurLaneApi.getShipByName(args.drop(1).joinToString(" "))
            sendMessage(event.channel, AzurLaneCommandModule.skinEmbed(ship,  args.first().toInt()))
        } catch (e: ApiException) {
            sendMessage(event.channel, "Not found")
            return
        }
    }
}