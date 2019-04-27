package core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import modules.AbstractModule;
import modules.GeneralModule;
import modules.azurlane.AzurLaneModule;
import modules.blindtest.BlindtestModule;
import modules.llsif.LLModule;
import modules.birthday.Birthday;
import modules.music.MusicModule;
import modules.picture.PictureModule;
import utils.BotUtils;

public class CommandHandler {

    // A map of modules mapping from module name to the map of commands
    static public Map<String, AbstractModule> moduleMap = new HashMap<>();

    // Thread pool
    static final public ExecutorService service = Executors.newFixedThreadPool(BotUtils.NB_THREAD);

    CommandHandler() {

		moduleMap.put("general", new GeneralModule());
		moduleMap.put("music", new MusicModule());
		moduleMap.put("blindtest", new BlindtestModule());
		moduleMap.put("picture", new PictureModule());
		moduleMap.put("llsif", new LLModule());
		moduleMap.put("al", new AzurLaneModule());
		moduleMap.put("birthday", new Birthday());

		// alias
		moduleMap.put("m", moduleMap.get("music"));
		moduleMap.put("b", moduleMap.get("blindtest"));
		moduleMap.put("p", moduleMap.get("picture"));
		moduleMap.put("ll", moduleMap.get("llsif"));
    }

}
