package bot.interfaces;

import java.util.Map;

public interface IModule {
    
    // Interface for a module 
    
    Map<String, ICommand> getCommands();
    
    void populate();
}
