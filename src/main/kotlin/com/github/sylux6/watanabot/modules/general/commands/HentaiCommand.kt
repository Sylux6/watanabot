package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.buildEmbedImageOnly
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object HentaiCommand : AbstractCommand("hentai") {
    override val template: String
        get() = ""
    override val description: String
        get() = "I'm not HENTAI."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        sendMessage(event.channel, buildEmbedImageOnly("https://i.imgur.com/9LOXen3.png"))
    }
}
