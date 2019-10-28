package manager

import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.user.UserActivityEndEvent
import net.dv8tion.jda.api.events.user.UserActivityStartEvent
import net.dv8tion.jda.api.events.user.update.UserUpdateActivityOrderEvent
import utils.BotUtils
import utils.BotUtils.addRole
import utils.BotUtils.removeRole

object StreamStatusManager {

    fun onStream(guild: Guild, member: Member, event: UserActivityStartEvent) {
        if (guild.idLong != BotUtils.SRID)
            return
        val role = guild.getRolesByName("On Live", false).first()

        if (event.newActivity.type == Activity.ActivityType.STREAMING)
            addRole(guild, member, role)
        else
            removeRole(guild, member, role)
    }

    fun updateStream(guild: Guild, member: Member, event: UserUpdateActivityOrderEvent) {
        if (guild.idLong != BotUtils.SRID)
            return
        val role = guild.getRolesByName("On Live", false).first()

        if (event.newValue.first().type != Activity.ActivityType.STREAMING) {
            removeRole(guild, member, role)
        }
    }

    fun leaveStream(guild: Guild, member: Member) {
        if (guild.idLong != BotUtils.SRID)
            return
        val role = guild.getRolesByName("On Live", false).first()
        removeRole(guild, member, role)
    }
}
