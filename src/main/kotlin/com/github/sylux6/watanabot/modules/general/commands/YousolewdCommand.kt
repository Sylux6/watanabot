package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object YousolewdCommand : AbstractCommand("yousolewd", levelAccess = listOf(CommandLevelAccess.PRIVATE)) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get lewd role."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val eventRole = event.guild.getRolesByName("Lewd", false).first()
        if (!event.member!!.roles.contains(eventRole)) {
            event.guild.addRoleToMember(event.member!!, eventRole).queue()
            sendMessage(
                event.channel,
                "${mentionAt(event.author)} you're lewd >///< (**Lewd** role added)"
            )
        } else {
            event.guild.removeRoleFromMember(event.member!!, eventRole).queue()
            sendMessage(event.channel, "${mentionAt(event.author)} you're pure (**Lewd** role removed)")
        }
    }
}
