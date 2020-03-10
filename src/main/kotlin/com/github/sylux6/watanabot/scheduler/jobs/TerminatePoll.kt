package com.github.sylux6.watanabot.scheduler.jobs

import com.github.sylux6.watanabot.modules.poll.utils.closeEmote
import com.github.sylux6.watanabot.modules.poll.utils.closePoll
import com.github.sylux6.watanabot.modules.poll.utils.pollMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.quartz.Job
import org.quartz.JobExecutionContext

class TerminatePoll : Job {
    override fun execute(context: JobExecutionContext) {
        runBlocking {
            withContext(Dispatchers.Default) {
                pollMap.forEach { (_, poll) ->
                    launch {
                        if (poll.hasExpired()) {
                            closePoll(poll)
                            poll.message.clearReactions(closeEmote).queue()
                        }
                    }
                }
            }
        }
    }
}
