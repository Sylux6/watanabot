package com.github.sylux6.watanabot.core.events.user

import com.github.sylux6.watanabot.utils.removeRole
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.user.UserActivityEndEvent

fun onUserActivityEndEvent(event: UserActivityEndEvent) {
    // Remove On Live role
    if (event.member.activities.map { it.type }.none { it == Activity.ActivityType.STREAMING }) {
        removeRole(event.guild, event.member, event.guild.getRolesByName("On Live", false).getOrElse(0) { return })
    }
}
