package commands.picture

import internal.commands.AbstractCommand
import commands.picture.PictureCommandModule.getImage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.kodehawa.lib.imageboards.entities.Rating

object SafeCommand : AbstractCommand("safe", 1) {
    override val template: String
        get() = "<terms>..."
    override val description: String
        get() = "Search for a picture from Danbooru."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        getImage(event, args, Rating.SAFE)
    }
}