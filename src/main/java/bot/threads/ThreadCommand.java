package bot.threads;

import java.util.List;

import modules.AbstractModule;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class ThreadCommand implements Runnable {
    
    AbstractModule module;
    MessageReceivedEvent event;
    List<String> argTab;
    
    public ThreadCommand(AbstractModule module, MessageReceivedEvent event, List<String> argTab) {
	this.module = module;
	this.event = event;
	this.argTab = argTab;
    }

    @Override
    public void run() {
	module.getMapCommands().get(argTab.get(0)).runCommand(event, argTab);
    }

}