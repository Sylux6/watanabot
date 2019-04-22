package core.listeners;

import manager.StreamStatusManager;
import net.dv8tion.jda.core.events.user.update.UserUpdateGameEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GameListener extends ListenerAdapter {

    @Override
    public void onUserUpdateGame(UserUpdateGameEvent event) {
        // Streaming
        StreamStatusManager.onStream(event.getGuild(), event.getMember(), event);
    }
}
