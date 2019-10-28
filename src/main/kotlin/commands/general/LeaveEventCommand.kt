package commands.general

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.MessageUtils
import utils.MessageUtils.sendMessage

object LeaveEventCommand : AbstractCommand("leaveevent", privateAccess = true) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Leave the ongoing event, ask mod team for more details."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val eventRole = event.guild.getRolesByName("Event", false).first()
        if (event.member!!.roles.contains(eventRole)) {
            event.guild.removeRoleFromMember(event.member!!, eventRole).queue()
            sendMessage(event.channel, "${MessageUtils.mentionAt(event.author)} "
                    + "${eventRole.name} role removed")
        }
    }
}