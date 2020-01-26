package com.github.sylux6.watanabot.manager

import com.github.sylux6.watanabot.utils.SRID
import com.github.sylux6.watanabot.utils.addRole
import com.github.sylux6.watanabot.utils.removeRole
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.user.UserActivityStartEvent

object StreamStatusManager {

    fun onStream(guild: Guild, member: Member, event: UserActivityStartEvent) {
        if (guild.idLong != SRID)
            return
        val role = guild.getRolesByName("On Live", false).first()

        if (event.newActivity.type == Activity.ActivityType.STREAMING)
            addRole(guild, member, role)
        else
            removeRole(guild, member, role)
    }

    fun leaveStream(guild: Guild, member: Member) {
        if (guild.idLong != SRID)
            return
        val role = guild.getRolesByName("On Live", false).first()
        removeRole(guild, member, role)
    }
}
