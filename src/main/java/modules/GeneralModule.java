package modules;

import java.util.Map;
import java.util.Random;

import bot.BotUtils;
import bot.interfaces.ICommand;

public class GeneralModule extends AbstractModule {

    public GeneralModule(String name) {
	super(name);
    }

    @Override
    public Map<String, ICommand> getCommands() {
	return commands;
    }

    @Override
    public void populate() {
	commands.put("testcommand", (event, args) -> {
	    BotUtils.sendMessage(event.getChannel(), "You ran the test command with args: " + args);
	});

	commands.put("ping", (event, args) -> {
	    BotUtils.sendMessage(event.getChannel(), "pong");
	});
	
	commands.put("orvus", (event, args) -> {
	    BotUtils.sendMessage(event.getChannel(), "@orvus#7455");
	});
	
	commands.put("roll", (event, args) -> {
	    BotUtils.sendMessage(event.getChannel(), ""+roll());
	});

    }
    
    // Auxiliar functions:
    
    private int roll() {
	Random rand = new Random();
	rand.setSeed(System.currentTimeMillis());
	return Math.abs((rand.nextInt()%101));
    }

}
