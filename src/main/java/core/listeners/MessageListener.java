package core.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.CommandHandler;
import modules.AbstractModule;
import modules.blindtest.BlindtestModule;
import modules.music.MusicModule;
import modules.picture.PictureModule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import threads.ThreadCommand;
import threads.ThreadGeneralBehaviour;
import threads.ThreadMentionBehaviour;
import threads.ThreadPictureDefaultBehaviour;
import utils.BotUtils;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

	// Ignore messages from BOT
	if (event.getMessage().getAuthor().isBot())
	    return;

	// Get all elements of the received message separated by blankspaces
	List<String> tmp = new ArrayList<String>(Arrays.asList(event.getMessage().getContentDisplay().split("\\s+")));

	// First ensure at least the command and prefix is present, the arg length can
	// be handled by your command func
	if (tmp.size() == 0)
	    return;

	// Check if the first arg (the command) starts with the prefix defined in the
	// utils class
	if (!tmp.get(0).startsWith(BotUtils.BOT_PREFIX)) {
	    if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser()))
		CommandHandler.service.execute(new ThreadMentionBehaviour(event));
	    else
		CommandHandler.service.execute(new ThreadGeneralBehaviour(event));
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
	AbstractModule module;

	// No specific module found
	if ((module = CommandHandler.moduleMap.get(argTab.get(0))) == null) {
	    module = CommandHandler.moduleMap.get("general"); // Take general module by default
	} else {
	    if (argTab.size() < 2) // Message contains at least <module> <command>
		return;
	    argTab.remove(0); // Module name removed
	}
	
	if (module instanceof MusicModule) { // Block MusicModule commands if Blindtest is running
	    BlindtestModule blindtestModule = (BlindtestModule) CommandHandler.moduleMap.get("blindtest");
	    if (blindtestModule.getBlindtestInstance(event.getGuild()) != null) {
		BotUtils.sendMessage(event.getChannel(), "A blindtest game is running");
		return;
	    }
	}

	// Calling the command
	if (module.getMapCommands().containsKey(argTab.get(0))) {
	    CommandHandler.service.execute(new ThreadCommand(module, event, argTab));
	} else if (module instanceof PictureModule) { // Getting image by default if unknown command
	    CommandHandler.service.execute(new ThreadPictureDefaultBehaviour(event, argTab));
	}
    }
}
