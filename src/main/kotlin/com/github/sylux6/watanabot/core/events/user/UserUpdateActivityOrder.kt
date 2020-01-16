package com.github.sylux6.watanabot.core.events.user

import com.github.sylux6.watanabot.utils.addRole
import com.github.sylux6.watanabot.utils.removeRole
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.user.update.UserUpdateActivityOrderEvent

fun onUserUpdateActivityOrder(event: UserUpdateActivityOrderEvent) {
    // Add/remove On Live role
    if (event.newValue.none { it.type == Activity.ActivityType.STREAMING }) {
        removeRole(event.guild, event.member, event.guild.getRolesByName("On Live", false).getOrElse(0) { return })
    } else {
        addRole(event.guild, event.member, event.guild.getRolesByName("On Live", false).getOrElse(0) { return })
    }
}