package modules;

import java.util.HashMap;
import java.util.Map;

import bot.interfaces.ICommand;

public abstract class AbstractModule implements bot.interfaces.IModule {
    
    // Abstract class for creating commands module
    
    protected Map<String, ICommand> commands;
    
    public AbstractModule() {
	this.commands = new HashMap<>();
	// populating map of commands
	populate();
    }
    
    public Map<String, ICommand> getMapCommands () {
	return commands;
    }
    
}
