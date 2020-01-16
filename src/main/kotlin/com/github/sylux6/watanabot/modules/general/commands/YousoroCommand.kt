package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.modules.picture.PictureCommandModule.getImage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.kodehawa.lib.imageboards.entities.Rating

object YousoroCommand : AbstractCommand("yousoro") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Send a picture of the best waifu."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        getImage(event, listOf("watanabe_you"), if (event.textChannel.isNSFW) Rating.EXPLICIT else Rating.SAFE)
    }
}