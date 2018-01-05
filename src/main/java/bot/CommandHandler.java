package bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bot.threads.ThreadCommand;
import bot.threads.ThreadReaction;
import modules.AbstractModule;
import modules.GeneralModule;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandHandler {

    // A map of modules mapping from module name to the map of commands
    private Map<String, AbstractModule> moduleMap;
    
    // Thread pool
    final ExecutorService service = Executors.newFixedThreadPool(100);

    public CommandHandler() {
	moduleMap = new HashMap<>();
	
	// Init
	moduleMap.put("general", new GeneralModule("general"));
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {

	// Get all elements of the received message separated by blankspaces
	List<String> tmp = new ArrayList<>(Arrays.asList(event.getMessage().getContent().split("\\s+")));

	// First ensure at least the command and prefix is present, the arg length can
	// be handled by your command func
	if (tmp.size() == 0)
	    return;

	// Check if the first arg (the command) starts with the prefix defined in the
	// utils class
	if (!tmp.get(0).startsWith(BotUtils.BOT_PREFIX)) {
	    service.execute(new ThreadReaction(event));
	    return;
	}

	List<String> argTab;

	if (tmp.get(0).equals(BotUtils.BOT_PREFIX)) {
	    argTab = tmp.subList(1, tmp.size());
	} else {
	    argTab = tmp;
	    argTab.set(0, argTab.get(0).substring(BotUtils.PREFIX_LENGTH));
	}

	// Get the module map of commands
	AbstractModule module = moduleMap.get(argTab.get(0));

	// No specific module found
	if (module == null) {
	    module = moduleMap.get("general"); // Taking general module by default
	} else {
	    if (argTab.size() < 2) // Message contains at least <module> <command>
		return;
	    argTab.remove(0); // Module name removed
	}
	
	// Calling the command
	if (module.getMapCommands().containsKey(argTab.get(0)))
	    service.execute(new ThreadCommand(module, event, argTab));
    }
    
}
