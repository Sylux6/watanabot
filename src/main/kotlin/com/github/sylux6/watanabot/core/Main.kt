package com.github.sylux6.watanabot.core

import club.minnced.jda.reactor.on
import com.github.sylux6.watanabot.core.events.initReactiveEventManager
import com.github.sylux6.watanabot.core.events.manager
import com.github.sylux6.watanabot.reminder.QuartzScheduler
import com.github.sylux6.watanabot.utils.bot_token
import com.github.sylux6.watanabot.utils.config
import com.github.sylux6.watanabot.utils.jda
import com.natpryce.konfig.Misconfiguration
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ShutdownEvent
import kotlin.system.exitProcess

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Building bot
        initReactiveEventManager()
        jda = JDABuilder(AccountType.BOT)
            .setToken(getToken())
            .setActivity(Activity.playing("with Chika-chan"))
            .setGuildSubscriptionsEnabled(true)
            .setEventManager(manager)
            .build()
        jda.on<ShutdownEvent>().subscribe { it.jda.httpClient.connectionPool().evictAll() }

        // Running scheduler
        QuartzScheduler.run()
    }

    private fun getToken(): String {
        try {
            return config[bot_token]
        } catch (e: Misconfiguration) {
            println("Please define a bot.token property in watanabot.properties file")
            exitProcess(1)
        }
    }
}
