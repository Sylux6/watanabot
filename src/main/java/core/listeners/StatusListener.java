package core.listeners;

import net.dv8tion.jda.core.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class StatusListener extends ListenerAdapter {

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
	    System.out.println(event.getMember().getNickname()+" "+event.getNewOnlineStatus().toString());
    }
}
