package core;

import core.listeners.GameListener;
import core.listeners.LeaverListener;
import core.listeners.MessageListener;
import core.listeners.ReactionListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import reminder.QuartzScheduler;
import utils.BotUtils;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args)
        throws LoginException, IllegalArgumentException, InterruptedException {

        if (args.length != 1) {
            System.out.println("Please enter the bot token in argument");
            return;
        }

        // Preparing modules
        new CommandHandler();

        // Building bot
        BotUtils.bot = new JDABuilder(AccountType.BOT).setToken(args[0])
                .addEventListeners(
                        new MessageListener(),
                        new GameListener(),
                        new LeaverListener(),
                        new ReactionListener()
                )
                .setActivity(Activity.playing("with Chika-chan"))
                .build()
                .awaitReady();

        QuartzScheduler sched = new QuartzScheduler();
        try {
            sched.run();
        } catch (Exception e) {
        }
    }

}
