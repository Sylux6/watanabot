package core.listeners;

import db.model.Member;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.BotUtils;
import utils.DBUtils;

public class LeaverListener extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Member member = new Member(event.getMember().getUser().getIdLong(), event.getGuild().getIdLong());
        DBUtils.delete(member);
        if (event.getGuild().getIdLong() == BotUtils.SRID) {
            BotUtils.sendLog(event.getUser().getName() + " has left");
        }
    }
}
