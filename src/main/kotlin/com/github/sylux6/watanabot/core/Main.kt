package com.github.sylux6.watanabot.core

import club.minnced.jda.reactor.on
import com.github.sylux6.watanabot.core.events.initReactiveEventManager
import com.github.sylux6.watanabot.core.events.manager
import com.github.sylux6.watanabot.reminder.QuartzScheduler
import com.github.sylux6.watanabot.utils.jda
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
            .setToken(getToken(args))
            .setActivity(Activity.playing("with Chika-chan"))
            .setGuildSubscriptionsEnabled(true)
            .setEventManager(manager)
            .build()
        jda.on<ShutdownEvent>().subscribe { it.jda.httpClient.connectionPool().evictAll() }

        // Running scheduler
        QuartzScheduler.run()
    }

    private fun getToken(args: Array<String>): String {
        try {
            return args[0]
        } catch (e: IndexOutOfBoundsException) {
            println("Please enter the bot token in argument")
            exitProcess(1)
        }
    }
}
