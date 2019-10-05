package interfaces;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public interface ICommand {

    // Interface for a command to be implemented in the command map
    void runCommand(MessageReceivedEvent event, List<String> args);
    
}
