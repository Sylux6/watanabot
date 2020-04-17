package com.github.sylux6.watanabot.core.events

import club.minnced.jda.reactor.ReactiveEventManager
import club.minnced.jda.reactor.createManager
import club.minnced.jda.reactor.on
import com.github.sylux6.watanabot.core.events.guild.onGuildMemberLeaveEvent
import com.github.sylux6.watanabot.core.events.message.onMessageAddReaction
import com.github.sylux6.watanabot.core.events.message.onMessageDelete
import com.github.sylux6.watanabot.core.events.message.onMessageReceivedEvent
import com.github.sylux6.watanabot.core.events.message.onMessageRemoveReaction
import com.github.sylux6.watanabot.core.events.user.onUserUpdateActivityEnd
import com.github.sylux6.watanabot.core.events.user.onUserUpdateActivityStart
import com.github.sylux6.watanabot.utils.isPrivateServer
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ScheduledExecutorService
import kotlin.concurrent.thread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.events.message.MessageDeleteEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent
import net.dv8tion.jda.api.events.user.UserActivityEndEvent
import net.dv8tion.jda.api.events.user.UserActivityStartEvent
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers

var count: Int = 0
val executor: ScheduledExecutorService =
    Executors.newScheduledThreadPool(ForkJoinPool.getCommonPoolParallelism()) {
        thread(start = false, block = it::run, name = "jda-thread-${count++}", isDaemon = true)
    }
val schedulerWrap: Scheduler = Schedulers.fromExecutor(executor)
val manager: ReactiveEventManager = createManager {
    scheduler = schedulerWrap
    isDispose = false
}
val eventScope = CoroutineScope(Dispatchers.Default)

/**
 * Launch a coroutine to handle an event
 * @param job function
 */
fun launchEvent(job: () -> Unit) {
    eventScope.launch { job() }
}

fun initReactiveEventManager() {
    manager.on<MessageReceivedEvent>()
        .filter { !it.message.author.isBot && !it.message.author.isFake }
        .subscribe { launchEvent { onMessageReceivedEvent(it) } }

    manager.on<GuildMemberRemoveEvent>()
        .filter { isPrivateServer(it.guild.idLong) }
        .subscribe { launchEvent { onGuildMemberLeaveEvent(it) } }

    manager.on<UserActivityStartEvent>()
        .filter { isPrivateServer(it.guild.idLong) }
        .subscribe { launchEvent { onUserUpdateActivityStart(it) } }

    manager.on<UserActivityEndEvent>()
        .filter { isPrivateServer(it.guild.idLong) }
        .subscribe { launchEvent { onUserUpdateActivityEnd(it) } }

    manager.on<MessageReactionAddEvent>()
        .filter { !it.user!!.isBot }
        .subscribe { launchEvent { onMessageAddReaction(it) } }

    manager.on<MessageReactionRemoveEvent>()
        .filter { !it.user!!.isBot }
        .subscribe { launchEvent { onMessageRemoveReaction(it) } }

    manager.on<MessageDeleteEvent>()
        .subscribe { launchEvent { onMessageDelete(it) } }
}
