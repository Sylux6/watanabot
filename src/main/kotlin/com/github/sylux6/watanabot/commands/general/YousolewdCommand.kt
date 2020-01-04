package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.MessageUtils
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessage

object YousolewdCommand : AbstractCommand("yousolewd", privateAccess = true) {
    override val template: String
        get() = ""
    override val description: String
        get() = "Get lewd role."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val eventRole = event.guild.getRolesByName("Lewd", false).first()
        if (!event.member!!.roles.contains(eventRole)) {
            event.guild.addRoleToMember(event.member!!, eventRole).queue()
            sendMessage(event.channel, "${MessageUtils.mentionAt(event.author)} you're lewd >///< (**Lewd** role added)")
        } else {
            event.guild.removeRoleFromMember(event.member!!, eventRole).queue()
            sendMessage(event.channel, "${MessageUtils.mentionAt(event.author)} you're pure (**Lewd** role removed)")
        }
    }
}