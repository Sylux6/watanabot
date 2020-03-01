package com.github.sylux6.watanabot.core

import club.minnced.jda.reactor.on
import com.github.sylux6.watanabot.core.events.executor
import com.github.sylux6.watanabot.core.events.initReactiveEventManager
import com.github.sylux6.watanabot.core.events.manager
import com.github.sylux6.watanabot.modules.poll.utils.initPollsFromDb
import com.github.sylux6.watanabot.scheduler.QuartzScheduler
import com.github.sylux6.watanabot.utils.SENTRY_DNS
import com.github.sylux6.watanabot.utils.config
import com.github.sylux6.watanabot.utils.getToken
import com.github.sylux6.watanabot.utils.jda
import db.utils.connectToDatabase
import io.sentry.Sentry
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ShutdownEvent

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Sentry logger setup
        if (config.contains(SENTRY_DNS)) {
            Sentry.init(config[SENTRY_DNS])
        }
        // Running job scheduler
        QuartzScheduler.run()
        // Database connection
        connectToDatabase()
        // Building bot
        initReactiveEventManager()
        jda = JDABuilder(AccountType.BOT)
            .setToken(getToken())
            .setActivity(Activity.playing("with Chika-chan"))
            .setGuildSubscriptionsEnabled(true)
            .setEventManager(manager)
            .setRateLimitPool(executor)
            .setGatewayPool(executor)
            .build()
        jda.on<ShutdownEvent>().subscribe { it.jda.httpClient.connectionPool().evictAll() }
        jda.awaitReady()
        // Init polls
        initPollsFromDb()
    }
}
