package com.github.sylux6.watanabot.core.events.user

import com.github.sylux6.watanabot.utils.removeRole
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.user.UserActivityEndEvent

fun onUserUpdateActivityEnd(event: UserActivityEndEvent) {
    // Remove On Live role
    if (event.member.activities.map { it.type }.none { it == Activity.ActivityType.STREAMING }) {
        event.guild.getRolesByName("On Live", false).getOrNull(0)?.let { role ->
            removeRole(event.guild, event.member, role)
        }
    }
}
