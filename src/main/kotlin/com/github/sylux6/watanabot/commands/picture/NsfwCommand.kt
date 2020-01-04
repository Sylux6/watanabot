package com.github.sylux6.watanabot.commands.picture

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.commands.picture.PictureCommandModule.getImage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.kodehawa.lib.imageboards.entities.Rating
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage

object NsfwCommand : AbstractCommand("nsfw", 1) {
    override val template: String
        get() = "<terms>..."
    override val description: String
        get() = "Search for a NSFW picture from Danbooru. You must be in a NSFW channel."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (event.textChannel.isNSFW)
            getImage(event, args, Rating.EXPLICIT)
        else
            sendMessage(event.channel, "You are not in a NSFW channel. You lewd!")
    }
}