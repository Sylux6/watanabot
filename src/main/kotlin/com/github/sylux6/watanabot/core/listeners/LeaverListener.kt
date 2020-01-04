package com.github.sylux6.watanabot.core.listeners

import com.github.sylux6.watanabot.internal.models.Member
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import com.github.sylux6.watanabot.utils.BotUtils
import com.github.sylux6.watanabot.utils.DBUtils
import com.github.sylux6.watanabot.utils.MessageUtils

class LeaverListener : ListenerAdapter() {

    override fun onGuildMemberLeave(event: GuildMemberLeaveEvent) {
        val member = Member(event.member.user.idLong, event.guild.idLong)
        DBUtils.delete(member)
        if (event.guild.idLong == BotUtils.SRID) {
            MessageUtils.sendLog(event.user.name + " has left")
        }
    }
}
