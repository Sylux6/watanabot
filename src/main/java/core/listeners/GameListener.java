package core.listeners;

import manager.StreamStatusManager;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameListener extends ListenerAdapter {

    @Override
    public void onUserActivityStart(UserActivityStartEvent event) {
        // Streaming
        StreamStatusManager.onStream(event.getGuild(), event.getMember(), event);
    }

    @Override
    public void onUserActivityEnd(UserActivityEndEvent event) {
        // Streaming
        StreamStatusManager.leaveStream(event.getGuild(), event.getMember(), event);
    }
}
