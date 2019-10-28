package commands.birthday

import internal.commands.AbstractCommand
import internal.models.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.DBUtils.query
import utils.DBUtils.saveOrUpdate
import utils.MessageUtils.sendMessageAt

object RemoveCommand : AbstractCommand("remove") {
    override val template: String
        get() = ""
    override val description: String
        get() = "Remove your birthday."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val member = Member(event.author.idLong, event.guild.idLong, null)
        val res = query("select id from member where userid = ${event.author.id} and guildid = ${event.guild.id}")
        if (res.isNotEmpty()) {
            member.setId(res[0] as Int)
        }
        saveOrUpdate(member)
        sendMessageAt(event.channel, event.author, "Your birthday has been removed")
    }
}