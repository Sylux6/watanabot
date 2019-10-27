package core

import core.listeners.GameListener
import core.listeners.LeaverListener
import core.listeners.MessageListener
import core.listeners.ReactionListener
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import reminder.QuartzScheduler
import utils.BotUtils

import javax.security.auth.login.LoginException

object Main {

    @Throws(LoginException::class, IllegalArgumentException::class, InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        if (args.size != 1) {
            println("Please enter the bot token in argument")
            return
        }

        // Preparing modules
        CommandHandler()

        // Building bot
        BotUtils.bot = JDABuilder(AccountType.BOT).setToken(args[0])
                .addEventListeners(
                        MessageListener(),
                        GameListener(),
                        LeaverListener(),
                        ReactionListener()
                )
                .setActivity(Activity.playing("with Chika-chan"))
                .build()
                .awaitReady()

        val sched = QuartzScheduler()
        try {
            sched.run()
        } catch (e: Exception) {
        }

    }

}
