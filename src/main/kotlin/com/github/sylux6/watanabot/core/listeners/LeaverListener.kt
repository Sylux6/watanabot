package com.github.sylux6.watanabot.core.listeners

import com.github.sylux6.watanabot.internal.models.Member
import com.github.sylux6.watanabot.utils.bot.SRID
import com.github.sylux6.watanabot.utils.misc.delete
import com.github.sylux6.watanabot.utils.message.sendLog
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class LeaverListener : ListenerAdapter() {

    override fun onGuildMemberLeave(event: GuildMemberLeaveEvent) {
        val member = Member(event.member.user.idLong, event.guild.idLong)
        delete(member)
        if (event.guild.idLong == SRID) {
            sendLog(event.user.name + " has left")
        }
    }
}
