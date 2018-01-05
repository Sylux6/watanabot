package bot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

public class BotUtils {

    // Constant prefix --CHANGE HERE--
    static String BOT_PREFIX = "o7";
    static int PREFIX_LENGTH = BOT_PREFIX.length();
    
    // Handles the creation and getting of a IDiscordClient object for a token
    static IDiscordClient getBuiltDiscordClient(String token) {

	// The ClientBuilder object is where you will attach your params for configuring
	// the instance of your bot.
	// Such as withToken, setDaemon etc
	return new ClientBuilder().withToken(token).build();

    }
    
    // Bot functions:
    
    // Function for sending message in the specified channel
    static public void sendMessage(IChannel channel, String message) {
	RequestBuffer.request(() -> {
	    try {
		channel.sendMessage(message); // Message starts with blankspace to avoid conflicts
	    } catch (DiscordException e) {
		System.err.println("Message could not be sent with error: ");
		e.printStackTrace();
	    }
	});
	
    }
    
    // Checks if a yousoro emoji exists
    static boolean yousoroEmojiExists(IGuild guild) {
	return guild.getEmojiByName("yousoro") != null;
    }
    
    // Returns emoji syntax for message
    static public String getEmoji(IGuild guild, String emoji) {
	return "<:"+emoji+":"+guild.getEmojiByName(emoji).getLongID()+">";
    }

}
