package core.listeners

import internal.models.Member
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import utils.BotUtils
import utils.DBUtils
import utils.MessageUtils

class LeaverListener : ListenerAdapter() {

    override fun onGuildMemberLeave(event: GuildMemberLeaveEvent) {
        val member = Member(event.member.user.idLong, event.guild.idLong)
        DBUtils.delete(member)
        if (event.guild.idLong == BotUtils.SRID) {
            MessageUtils.sendLog(event.user.name + " has left")
        }
    }
}
