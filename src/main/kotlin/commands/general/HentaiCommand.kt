package commands.general

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.EmbedUtils
import utils.MessageUtils.sendMessage

object HentaiCommand : AbstractCommand("hentai") {
    override val template: String
        get() = ""
    override val description: String
        get() = "I'm not HENTAI."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        sendMessage(event.channel, EmbedUtils.buildEmbedImageOnly("https://i.imgur.com/9LOXen3.png"))
    }
}