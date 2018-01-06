package core;

import core.listeners.MentionListener;
import core.listeners.MessageListener;
import sx.blah.discord.api.IDiscordClient;

public class Main {

    public static void main(String[] args) {

	if (args.length != 1) {
	    System.out.println("Please enter the bot token as the first ");
	    return;
	}


	IDiscordClient cli = BotUtils.getBuiltDiscordClient(args[0]);

	// Register a listener via the EventSubscriber annotation which allows for
	// organisation and delegation of events
	cli.getDispatcher().registerListener(new CommandHandler());
	cli.getDispatcher().registerListener(new MessageListener());
	cli.getDispatcher().registerListener(new MentionListener());
	

	// Only login after all events are registered otherwise some may be missed.
	cli.changePlayingText("with Chika");
	cli.login();
    }

}
