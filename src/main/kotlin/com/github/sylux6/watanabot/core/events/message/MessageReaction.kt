package com.github.sylux6.watanabot.core.events.message

import com.github.sylux6.watanabot.modules.poll.utils.containsPoll
import com.github.sylux6.watanabot.modules.poll.utils.emoteToIndex
import com.github.sylux6.watanabot.modules.poll.utils.getPoll
import com.github.sylux6.watanabot.modules.poll.utils.initPoll
import com.github.sylux6.watanabot.modules.poll.utils.pollMap
import com.github.sylux6.watanabot.modules.poll.utils.refreshPoll
import com.github.sylux6.watanabot.modules.poll.utils.resetEmoji
import java.util.concurrent.CompletableFuture
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent

fun onMessageAddReaction(event: MessageReactionAddEvent) {
    if (pollMap.containsPoll(event)) {
        val poll = pollMap.getPoll(event)!!
        val completableFutures = mutableListOf<CompletableFuture<Void>>()
        // Reset poll
        if (event.reactionEmote.emoji == resetEmoji) {
            if (event.member == poll.author || event.member!!.hasPermission(Permission.MESSAGE_MANAGE)) {
                completableFutures.add(poll.message.clearReactions().submit().thenRun {
                    initPoll(poll)
                })
            } else {
                poll.message.removeReaction(event.reactionEmote.emote, event.user!!)
            }
        }
        // Remove previous vote if multiple choice is disabled
        else if (!poll.multipleChoice) {
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
