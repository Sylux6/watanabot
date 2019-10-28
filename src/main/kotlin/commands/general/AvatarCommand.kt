package commands.general

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils.findMember
import utils.EmbedUtils
import utils.MessageUtils.sendMessage

object AvatarCommand : AbstractCommand("avatar") {
    override val template: String
        get() = "[<member>]"
    override val description: String
        get() = "Get avatar from a member."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            sendMessage(event.channel, EmbedUtils.buildEmbedImageOnly(
                    event.author.name + "#" + event.author.discriminator,
                    event.author.effectiveAvatarUrl))
            return
        }
        val username = args.joinToString(" ")
        val member = findMember(event.guild, username)

        if (member != null) {
            sendMessage(event.channel, EmbedUtils.buildEmbedImageOnly(
                    member.user.name + "#" + event.author.discriminator,
                    member.user.effectiveAvatarUrl))
            return
        }
        sendMessage(event.channel, "Cannot find user in the server")
    }
}