package com.github.sylux6.watanabot.core.events.message

import com.github.sylux6.watanabot.modules.poll.utils.containsPoll
import com.github.sylux6.watanabot.modules.poll.utils.emoteToIndex
import com.github.sylux6.watanabot.modules.poll.utils.getPoll
import com.github.sylux6.watanabot.modules.poll.utils.pollMap
import com.github.sylux6.watanabot.modules.poll.utils.refreshPoll
import java.util.concurrent.CompletableFuture
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent

fun onMessageAddReaction(event: MessageReactionAddEvent) {
    if (pollMap.containsPoll(event)) {
        val poll = pollMap.getPoll(event)!!
        val completableFutures = mutableListOf<CompletableFuture<Void>>()
        // Remove previous vote if multiple choice is disabled
        if (!poll.multipleChoices) {
            for (emote in emoteToIndex.keys.filter { unicode -> event.reactionEmote.emoji != unicode }) {
                completableFutures.add(poll.message.removeReaction(emote, event.user!!).submit())
            }
        }
        CompletableFuture.allOf(*completableFutures.toTypedArray()).thenRun {
            refreshPoll(poll)
        }
    }
}

fun onMessageRemoveReaction(event: MessageReactionRemoveEvent) {
    if (pollMap.containsPoll(event)) {
        refreshPoll(pollMap.getPoll(event)!!)
    }
}
