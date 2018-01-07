package modules;

import java.util.HashMap;
import java.util.Map;

import interfaces.ICommand;

public abstract class AbstractModule implements interfaces.IModule {

    // Abstract class for creating commands module

    protected Map<String, ICommand> commands;
    protected String name;

    public AbstractModule() {
	this.commands = new HashMap<>();
	// populating map of commands
	populate();
    }

    public Map<String, ICommand> getMapCommands() {
	return commands;
    }

    public String getHelp() {
	StringBuilder help = new StringBuilder("**" + getName() + " module commands list**:\n");
	for (String s : commands.keySet()) {
	    help.append("- **" + s + "**\n");
	}
	return help.toString();
    }

}
