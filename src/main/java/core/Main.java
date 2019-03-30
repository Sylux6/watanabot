package core;

import core.listeners.GameListener;
import core.listeners.MessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import reminder.QuartzScheduler;
import utils.BotUtils;
import utils.DBUtils;

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
			.addEventListener(new MessageListener())
			.addEventListener(new GameListener())
			.setGame(Game.playing("with Chika-chan"))
			.build()
			.awaitReady();

		// Database initialization
		DBUtils.initDB();


		QuartzScheduler sched = new QuartzScheduler();
		try {
			sched.run();
		} catch (Exception e) {
			BotUtils.logger.error("", e);
		}
    }

}
