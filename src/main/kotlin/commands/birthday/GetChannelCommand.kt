package commands.birthday

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.DBUtils.query
import utils.MessageUtils.linkTextChannel
import utils.MessageUtils.sendMessage

object GetChannelCommand : AbstractCommand("getchannel") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get text channel in where birthdays are announced."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val res = query("select birthdaychannelid from settings where guildid = ${event.guild.id}")
        val channel = event.guild.getTextChannelById(res[0].toString())
        if (res.isEmpty() || channel == null) {
            sendMessage(event.channel, "Birthday channel is not set")
            return
        }
        sendMessage(event.channel, "Birthdays are announced in " + linkTextChannel(channel))
    }
}