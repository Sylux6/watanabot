package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.utils.buildEmbedImageOnly
import com.github.sylux6.watanabot.utils.findMember
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object AvatarCommand : AbstractCommand("avatar") {
    override val template: String
        get() = "[<member>]"
    override val description: String
        get() = "Get avatar from a member."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            sendMessage(
                event.channel, buildEmbedImageOnly(
                    event.author.name + "#" + event.author.discriminator,
                    event.author.effectiveAvatarUrl
                )
            )
            return
        }
        val username = args.joinToString(" ")
        val member = findMember(event.guild, username)

        if (member != null) {
            sendMessage(
                event.channel, buildEmbedImageOnly(
                    member.user.name + "#" + event.author.discriminator,
                    member.user.effectiveAvatarUrl
                )
            )
            return
        }
        sendMessage(event.channel, "Cannot find user in the server")
    }
}
