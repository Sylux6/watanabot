package core;

import javax.security.auth.login.LoginException;

import core.listeners.MessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Main {

    public static void main(String[] args)
	    throws LoginException, RateLimitedException, IllegalArgumentException, InterruptedException {

	if (args.length != 1) {
	    System.out.println("Please enter the bot token as the first ");
	    return;
	}	
	
	// Preparing modules
	new CommandHandler();	

	// Building bot
	new JDABuilder(AccountType.BOT).setToken(args[0])
		.addEventListener(new MessageListener())
		.setGame(Game.playing("with Chika-chan"))
		.buildBlocking();
    }	

}
