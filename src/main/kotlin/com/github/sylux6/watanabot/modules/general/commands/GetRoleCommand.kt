package com.github.sylux6.watanabot.modules.general.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.utils.sendDM
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object GetRoleCommand : AbstractCommand("getrole", 1) {
    override val template: String
        get() = "<role>"
    override val description: String
        get() = "Get list of members with the given role."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val roleName = args.joinToString(" ")
        val message = StringBuilder()
        val role: List<Role> = event.guild.getRolesByName(roleName, true)
        if (role.isEmpty()) {
            commandFail("**$roleName** role not found")
        }

        val l: List<Member> = event.guild.getMembersWithRoles(role.first())
        message.append("List of **${role.first().name}** (**${l.size}**):\n")

        for (u in l)
            message.append("- ${u.user.name}#${u.user.discriminator}\n")

        if (l.size > 10) {
            sendMessage(event.channel, "List of **$roleName** (**${l.size}**) sent in DM")
            sendDM(event.author, message.toString())
        } else {
            sendMessage(event.channel, message.toString())
        }
    }
}
