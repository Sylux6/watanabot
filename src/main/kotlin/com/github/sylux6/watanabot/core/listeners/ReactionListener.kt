package com.github.sylux6.watanabot.core.listeners

import com.github.sylux6.watanabot.utils.bot.bot
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ReactionListener : ListenerAdapter() {

    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (event.user === bot.selfUser) {
            return
        }
//        if (BotUtils.smashPassInstances.containsKey(event.messageId)) {
//            SmashPassModule.addReaction(event)
//        }
    }

    override fun onMessageReactionRemove(event: MessageReactionRemoveEvent) {
        if (event.user === bot.selfUser) {
            return
        }
//        if (BotUtils.smashPassInstances.containsKey(event.messageId)) {
//            SmashPassModule.removeReaction(event)
//        }
    }
}
