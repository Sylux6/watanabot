package interfaces;

import java.util.List;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface ICommand {

    // Interface for a command to be implemented in the command map
    void runCommand(MessageReceivedEvent event, List<String> args);
    
}
