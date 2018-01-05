package modules;

import java.util.Collections;
import java.util.Random;

import core.BotUtils;
import net.kodehawa.lib.imageboards.DefaultImageBoards;

public class GeneralModule extends AbstractModule {

    @Override
    public void populate() {
	commands.put("test", (event, args) -> {
	    BotUtils.sendMessage(event.getChannel(), "You ran the test command with args: " + args);
	});

	commands.put("roll", (event, args) -> {
	    if (BotUtils.yousoroEmojiExists(event.getGuild()))
		BotUtils.sendMessage(event.getChannel(), roll() + " " + BotUtils.getEmoji(event.getGuild(), "yousoro"));
	    else
		BotUtils.sendMessage(event.getChannel(), roll() + "");
	});

	commands.put("wait", (event, args) -> {
	    try {
		Thread.sleep(5000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    BotUtils.sendMessage(event.getChannel(), "waited 5 sec");
	});

	commands.put("nya", (event, args) -> {
	    DefaultImageBoards.SAFEBOORU.search(100, "nekomimi").async(images -> {
		Collections.shuffle(images);
		BotUtils.sendMessage(event.getChannel(), images.get(0).getURL());
	    });
	});

    }

    // Auxiliar functions:

    private int roll() {
	Random rand = new Random();
	rand.setSeed(System.currentTimeMillis());
	return Math.abs((rand.nextInt() % 100)) + 1;
    }

}
