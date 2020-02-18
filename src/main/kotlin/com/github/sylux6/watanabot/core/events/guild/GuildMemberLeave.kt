package com.github.sylux6.watanabot.core.events.guild

import com.github.sylux6.watanabot.utils.sendLog
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent

fun onGuildMemberLeaveEvent(event: GuildMemberLeaveEvent) {
    sendLog(event.user.name + " has left")
}
