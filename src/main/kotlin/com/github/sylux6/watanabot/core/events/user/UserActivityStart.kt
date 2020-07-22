package com.github.sylux6.watanabot.core.events.user

import com.github.sylux6.watanabot.utils.addRole
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.user.UserActivityStartEvent

fun onUserUpdateActivityStart(event: UserActivityStartEvent) {
    // Add On Live role
    if (event.newActivity.type == Activity.ActivityType.STREAMING) {
        event.guild.getRolesByName("On Live", false).getOrNull(0)?.let { role ->
            addRole(event.guild, event.member, role)
        }
    }
}
