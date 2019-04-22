package manager;

import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.user.update.UserUpdateGameEvent;
import utils.BotUtils;

public class StreamStatusManager {
    
    static public void onStream(Guild guild, Member member, UserUpdateGameEvent event) {
	if (guild.getIdLong() != BotUtils.SRID)
	    return;
	Role role = guild.getRolesByName("On Live", false).get(0);
	
	if (event.getNewGame() == null)
	    BotUtils.removeRole(guild, member, role);
	else if (event.getNewGame().getType() != GameType.STREAMING)
	    BotUtils.removeRole(guild, member, role);
	else
	    BotUtils.addRole(guild, member, role);
    }
}
