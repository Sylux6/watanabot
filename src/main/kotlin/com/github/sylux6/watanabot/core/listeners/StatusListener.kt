package com.github.sylux6.watanabot.core.listeners

import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class StatusListener : ListenerAdapter() {

    override fun onUserUpdateOnlineStatus(event: UserUpdateOnlineStatusEvent) {
        println(event.member.nickname + " " + event.newOnlineStatus.toString())
    }
}
