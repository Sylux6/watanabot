package manager;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import utils.BotUtils;

public class StreamStatusManager {
    
    static public void onStream(Guild guild, Member member, UserActivityStartEvent event) {
        if (guild.getIdLong() != BotUtils.SRID)
            return;
        Role role = guild.getRolesByName("On Live", false).get(0);

        if (event.getNewActivity().getType() == Activity.ActivityType.STREAMING)
            BotUtils.addRole(guild, member, role);
        else
            BotUtils.removeRole(guild, member, role);
    }

    static public void leaveStream(Guild guild, Member member, UserActivityEndEvent event) {
        if (guild.getIdLong() != BotUtils.SRID)
            return;
        Role role = guild.getRolesByName("On Live", false).get(0);
        BotUtils.removeRole(guild, member, role);
    }
}
