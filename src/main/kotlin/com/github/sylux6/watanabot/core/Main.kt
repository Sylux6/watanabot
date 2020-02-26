package com.github.sylux6.watanabot.core

import club.minnced.jda.reactor.on
import com.github.sylux6.watanabot.core.events.executor
import com.github.sylux6.watanabot.core.events.initReactiveEventManager
import com.github.sylux6.watanabot.core.events.manager
import com.github.sylux6.watanabot.scheduler.QuartzScheduler
import com.github.sylux6.watanabot.utils.CONFIG_TOKEN
import com.github.sylux6.watanabot.utils.SENTRY_DNS
import com.github.sylux6.watanabot.utils.config
import com.github.sylux6.watanabot.utils.jda
import com.natpryce.konfig.Misconfiguration
import db.utils.connectToDatabase
import io.sentry.Sentry
import kotlin.system.exitProcess
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

        // Running scheduler
        QuartzScheduler.run()

        // Database connection
        connectToDatabase()
    }

    private fun getToken(): String {
        try {
            return config[CONFIG_TOKEN]
        } catch (e: Misconfiguration) {
            println("Please define a bot.token property in watanabot.properties file")
            exitProcess(1)
        }
    }
}
