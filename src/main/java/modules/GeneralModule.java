package modules;

import java.util.Random;

import modules.picture.PictureModule;
import utils.BotUtils;

public class GeneralModule extends AbstractModule {

    @Override
    public void populate() {
	commands.put("test", (event, args) -> {
	    BotUtils.sendMessage(event.getChannel(), "You ran the test command with args: " + args);
	});

	commands.put("roll", (event, args) -> {
	    if (BotUtils.yousoroEmojiExists(event.getGuild()))
		BotUtils.sendMessage(event.getChannel(), BotUtils.mentionAt(event.getAuthor()) + " " + roll() + " "
			+ BotUtils.getEmojiMessage(event.getGuild(), "yousoro"));
	    else
		BotUtils.sendMessage(event.getChannel(), BotUtils.mentionAt(event.getAuthor()) + " " + roll() + "");
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
	    PictureModule.getImage(event, "nekomimi");
	});

	commands.put("mukyu", (event, args) -> {
	    PictureModule.getImage(event, "patchouli_knowledge");
	});

	commands.put("yousoro", (event, args) -> {
	    PictureModule.getImage(event, "watanabe_you");
	});

	commands.put("kanan", (event, args) -> {
	    PictureModule.getImage(event, "matsuura_kanan");
	});

	commands.put("zura", (event, args) -> {
	    PictureModule.getImage(event, "kunikida_hanamaru ");
	});

	commands.put("ganbaruby", (event, args) -> {
	    PictureModule.getImage(event, "kurosawa_ruby");
	});

    }

    // Auxiliar functions:

    private int roll() {
	Random rand = new Random();
	rand.setSeed(System.currentTimeMillis());
	return Math.abs((rand.nextInt() % 100)) + 1;
    }

}
