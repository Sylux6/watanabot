package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.MessageUtils
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object JoinEventCommand : AbstractCommand("joinevent", levelAccess = listOf(CommandLevelAccess.PRIVATE)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Join the ongoing event, ask mod team for more details."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val eventRole = event.guild.getRolesByName("Event", false).first()
        if (!event.member!!.roles.contains(eventRole)) {
            event.guild.addRoleToMember(event.member!!, eventRole).queue()
            sendMessage(
                event.channel, "${MessageUtils.mentionAt(event.author)} "
                    + "${eventRole.name} role added"
            )
        }
    }
}