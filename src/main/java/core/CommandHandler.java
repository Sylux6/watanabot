package core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import modules.AbstractModule;
import modules.GeneralModule;
import modules.Blindtest.BlindtestModule;
import modules.music.MusicModule;
import modules.picture.PictureModule;

public class CommandHandler {

    // A map of modules mapping from module name to the map of commands
    static public Map<String, AbstractModule> moduleMap = new HashMap<>();
    
    // Thread pool
    static final public ExecutorService service = Executors.newFixedThreadPool(100);

    public CommandHandler() {

	moduleMap.put("general", new GeneralModule());
	moduleMap.put("music", new MusicModule());
	moduleMap.put("blindtest", new BlindtestModule());
	moduleMap.put("picture", new PictureModule());
    }
    
}
