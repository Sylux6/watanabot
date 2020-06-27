package com.github.sylux6.watanabot.core.events.guild

import com.github.sylux6.watanabot.utils.log
import com.github.sylux6.watanabot.utils.sendLog
import mu.KotlinLogging
import net.dv8tion.jda.api.events.guild.GuildBanEvent

private val logger = KotlinLogging.logger {}

fun onGuildBanEvent(event: GuildBanEvent) {
    sendLog("${event.user.name} has been banned")
    logger.log("${event.user.asTag} has been banned from ${event.guild.name}")
}
