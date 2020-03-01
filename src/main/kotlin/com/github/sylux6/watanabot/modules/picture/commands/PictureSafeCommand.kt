package com.github.sylux6.watanabot.modules.picture.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.modules.picture.utils.getImage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.kodehawa.lib.imageboards.entities.Rating

object PictureSafeCommand : AbstractCommand("safe", 1) {
    override val template: String
        get() = "<terms>..."
    override val description: String
        get() = "Search for a picture from Danbooru."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        getImage(event, args, Rating.SAFE)
    }
}
