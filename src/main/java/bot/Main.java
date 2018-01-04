package bot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

public class Main {
    
    public static void main(String[] args) {
	
	String token = "";
	
	IDiscordClient client = Example.createClient(token, true); // Gets the client object (from the first example)
	EventDispatcher dispatcher = client.getDispatcher(); // Gets the EventDispatcher instance for this client
							     // instance
	dispatcher.registerListener(new AnnotationListener()); // Registers the @EventSubscriber example class from above
	SimpleCommand cmd = new SimpleCommand();
	cmd.enable(client);
    }

}
