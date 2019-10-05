package core.listeners;

import db.model.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BotUtils;
import utils.DBUtils;
import utils.MessageUtils;

public class LeaverListener extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Member member = new Member(event.getMember().getUser().getIdLong(), event.getGuild().getIdLong());
        DBUtils.delete(member);
        if (event.getGuild().getIdLong() == BotUtils.SRID) {
            MessageUtils.sendLog(event.getUser().getName() + " has left");
        }
    }
}
