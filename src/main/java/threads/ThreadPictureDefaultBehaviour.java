package threads;

import java.util.List;

import modules.picture.PictureModule;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ThreadPictureDefaultBehaviour implements Runnable {
    
    MessageReceivedEvent event;
    List<String> args;

    public ThreadPictureDefaultBehaviour(MessageReceivedEvent event, List<String> args) {
	this.event = event;
	this.args = args;
    }

    @Override
    public void run() {
	PictureModule.getByDefault(event, args);
    }

}
