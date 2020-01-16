package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.models.Member
import com.github.sylux6.watanabot.utils.query
import com.github.sylux6.watanabot.utils.saveOrUpdate
import com.github.sylux6.watanabot.utils.sendBotMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

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
        sendBotMessage(event.channel, "**${event.member!!.effectiveName}** birthday has been removed")
    }
}