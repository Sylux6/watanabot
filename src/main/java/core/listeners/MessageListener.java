package core.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.BotUtils;
import core.CommandHandler;
import modules.AbstractModule;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import threads.ThreadCommand;
import threads.ThreadReaction;

public class MessageListener {

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
	    CommandHandler.service.execute(new ThreadReaction(event));
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
	AbstractModule module = CommandHandler.moduleMap.get(argTab.get(0));

	// No specific module found
	if (module == null) {
	    module = CommandHandler.moduleMap.get("general"); // Taking general module by default
	} else {
	    if (argTab.size() < 2) // Message contains at least <module> <command>
		return;
	    argTab.remove(0); // Module name removed
	}
	
	// Calling the command
	if (module.getMapCommands().containsKey(argTab.get(0)))
	    CommandHandler.service.execute(new ThreadCommand(module, event, argTab));
    }
}
