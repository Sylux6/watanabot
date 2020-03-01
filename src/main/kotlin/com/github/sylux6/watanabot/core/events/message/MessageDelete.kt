package com.github.sylux6.watanabot.core.events.message

import com.github.sylux6.watanabot.modules.poll.utils.containsPoll
import com.github.sylux6.watanabot.modules.poll.utils.pollMap
import com.github.sylux6.watanabot.modules.poll.utils.removePoll
import com.github.sylux6.watanabot.modules.poll.utils.removePollFromDatabase
import net.dv8tion.jda.api.events.message.MessageDeleteEvent

fun onMessageDelete(event: MessageDeleteEvent) {
    if (pollMap.containsPoll(event)) {
        val poll = pollMap.removePoll(event)!!
        removePollFromDatabase(poll)
    }
}
