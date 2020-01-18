package com.github.sylux6.watanabot.core

import com.github.sylux6.watanabot.core.listeners.ActivityListener
import com.github.sylux6.watanabot.core.listeners.LeaverListener
import com.github.sylux6.watanabot.core.listeners.MessageListener
import com.github.sylux6.watanabot.core.listeners.ReactionListener
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import com.github.sylux6.watanabot.reminder.QuartzScheduler
import com.github.sylux6.watanabot.utils.BotUtils

object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        if (args.size != 1) {
            println("Please enter the bot token in argument")
            return
        }

        // Building bot
        BotUtils.bot = JDABuilder(AccountType.BOT).setToken(args[0])
                .addEventListeners(
                        MessageListener(),
                        ActivityListener(),
                        LeaverListener(),
                        ReactionListener()
                )
                .build()
                .awaitReady()
        BotUtils.randomStatus()

        QuartzScheduler.run()
    }

}
