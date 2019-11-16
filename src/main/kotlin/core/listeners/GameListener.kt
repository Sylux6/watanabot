package core.listeners

import manager.StreamStatusManager.leaveStream
import manager.StreamStatusManager.onStream
import net.dv8tion.jda.api.events.user.UserActivityEndEvent
import net.dv8tion.jda.api.events.user.UserActivityStartEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter


class GameListener : ListenerAdapter() {

    override fun onUserActivityStart(event: UserActivityStartEvent) {
        // Streaming
        onStream(event.guild, event.member, event)
    }

    override fun onUserActivityEnd(event: UserActivityEndEvent) {
        // Streaming
        leaveStream(event.guild, event.member)
    }
}
