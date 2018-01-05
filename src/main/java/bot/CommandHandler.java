package bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.interfaces.ICommand;
import bot.interfaces.IModule;
import modules.GeneralModule;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandHandler {

    // A map of modules mapping from module name to the map of commands
    private Map<String, IModule> moduleMap;

    public CommandHandler() {
	moduleMap = new HashMap<>();

	// Init
	moduleMap.put("general", new GeneralModule("general"));
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {

	// Get all elements of the received message separated by blankspaces
	String[] argArray = event.getMessage().getContent().split(" ");

	// First ensure at least the command and prefix is present, the arg length can
	// be handled by your command func
	if (argArray.length == 0)
	    return;

	// Check if the first arg (the command) starts with the prefix defined in the utils class
	if (!argArray[0].startsWith(BotUtils.BOT_PREFIX))
	    return;

	// Extract the module name or command part
	String firstPart = argArray[0].substring(2);
	firstPart.replace("//s+", ""); // Remove useless whitespaces (if the user didn't insert blankspace between
				       // prefix and command we want to handle it anyway)

	// Get the module map of commands
	IModule module = moduleMap.get(firstPart);

	// No specific module found
	if (module == null) {
	    module = moduleMap.get("general");
	}

	// Load the rest of the message in the array into a List for safer access
	List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
	if (firstPart.length() == 0) {
	    argsList.remove(0); // Remove the module name
	} else {
	    argsList.set(0, firstPart);
	}

	// Instead of delegating the work to a switch, automatically do it via calling
	// the mapping if it exists

	if (module.getCommands().containsKey(argsList.get(0)))
	    module.getCommands().get(argsList.get(0)).runCommand(event, argsList);

    }
}
