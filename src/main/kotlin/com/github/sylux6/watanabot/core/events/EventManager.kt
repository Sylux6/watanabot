package com.github.sylux6.watanabot.core.events

import club.minnced.jda.reactor.ReactiveEventManager
import club.minnced.jda.reactor.createManager
import club.minnced.jda.reactor.on
import com.github.sylux6.watanabot.core.events.guild.onGuildMemberLeaveEvent
import com.github.sylux6.watanabot.core.events.message.onMessageReceivedEvent
import com.github.sylux6.watanabot.core.events.user.onUserUpdateActivityOrder
import com.github.sylux6.watanabot.utils.isPrivateServer
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.user.update.UserUpdateActivityOrderEvent
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ScheduledExecutorService
import kotlin.concurrent.thread

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

fun initEventReactor() {
    manager.on<MessageReceivedEvent>()
        .filter { !it.message.author.isBot && !it.message.author.isFake }
        .subscribe { onMessageReceivedEvent(it) }

    manager.on<GuildMemberLeaveEvent>()
        .filter { isPrivateServer(it.guild.idLong) }
        .subscribe { onGuildMemberLeaveEvent(it) }

    manager.on<UserUpdateActivityOrderEvent>()
        .filter { isPrivateServer(it.guild.idLong) }
        .subscribe { onUserUpdateActivityOrder(it) }
}