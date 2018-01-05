package bot;

import java.util.List;

import bot.interfaces.IModule;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class ThreadCommand implements Runnable {
    
    IModule module;
    MessageReceivedEvent event;
    List<String> argTab;
    
    public ThreadCommand(IModule module, MessageReceivedEvent event, List<String> argTab) {
	this.module = module;
	this.event = event;
	this.argTab = argTab;
    }

    @Override
    public void run() {
	module.getCommands().get(argTab.get(0)).runCommand(event, argTab);
    }

}
