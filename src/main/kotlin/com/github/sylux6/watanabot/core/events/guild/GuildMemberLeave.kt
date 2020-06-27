package com.github.sylux6.watanabot.core.events.guild

import com.github.sylux6.watanabot.utils.log
import com.github.sylux6.watanabot.utils.sendLog
import mu.KotlinLogging
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent

private val logger = KotlinLogging.logger {}

fun onGuildMemberLeaveEvent(event: GuildMemberRemoveEvent) {
    sendLog("${event.user.name} has left")
    logger.log("${event.user.asTag} left ${event.guild.name}")
}
