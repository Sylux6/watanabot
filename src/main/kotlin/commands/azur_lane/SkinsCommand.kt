package commands.azur_lane

import com.github.azurapi.azurapikotlin.internal.exceptions.ApiException
import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils
import utils.MessageUtils.sendMessage

object SkinsCommand : AbstractCommand("skins", 1) {
    override val template: String
        get() = "<name>"
    override val description: String
        get() = "Get available skins of a ship."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        try {
            val ship = BotUtils.azurLaneApi.getShipByName(args.joinToString(" "))
            sendMessage(event.channel, AzurLaneCommandModule.skinListEmbed(ship))
        } catch (e: ApiException) {
            sendMessage(event.channel, "Not found")
            return
        }
    }
}