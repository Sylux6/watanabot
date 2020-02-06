package com.github.sylux6.watanabot.core.events.guild

import com.github.sylux6.watanabot.internal.models.Member
import com.github.sylux6.watanabot.utils.delete
import com.github.sylux6.watanabot.utils.sendLog
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent

fun onGuildMemberLeaveEvent(event: GuildMemberLeaveEvent) {
    val member = Member(event.member.user.idLong, event.guild.idLong)
    // TODO: Does it work ? (delete member from database)
    delete(member)
    sendLog(event.user.name + " has left")
}
