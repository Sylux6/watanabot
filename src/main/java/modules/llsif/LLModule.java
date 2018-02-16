package modules.llsif;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import modules.AbstractModule;
import modules.llsif.entity.Card;
import modules.llsif.entity.Idol;
import utils.BotUtils;
import utils.HttpRequest;
import utils.JsonUtils;

public class LLModule extends AbstractModule {
    
    public LLModule() {
	super();
    }

    @Override
    public void populate() {
	commands.put("info", (event, args) -> {
	    if (args.size() < 2)
		return;
	    for (Idol i : Idol.values()) {
		for (int j = 1; j < args.size(); j++) {
		    if (i.toString().matches("(.*(?i)"+args.get(j)+" .*)")) {
			BotUtils.sendMessageEmbed(event.getChannel(), i.toEmbed());
			return;
		    }
		}
	    }
	    BotUtils.sendMessage(event.getChannel(), "Idol not found");
	});
	
	commands.put("hscout", (event, args) -> {
	    BotUtils.random.setSeed(System.currentTimeMillis());
	    Card c;
	    JSONObject json;
	    JSONArray jsonArray;
	    String unit;
	    String rarity;
	    int roll;
	    int count;
	    
	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "u's / aqours argument missing");
		return;
	    }
	    
	    // Main unit scouting
	    switch(args.get(1)) {
	    case "u's":
		unit = "µ's";
		break;
	    case "aqours":
		unit = "aqours";
		break;
		default: 
		    BotUtils.sendMessage(event.getChannel(), "u's / aqours argument missing");
		    return;
	    }
	    
	    // RNG
	    roll = BotUtils.random.nextInt(100);
	    if (roll == 0)
		rarity = "UR";
	    else if (roll <= 4)
		rarity = "SSR";
	    else if (roll <= 20)
		rarity = "SR";
	    else
		rarity = "R";
	    
	    try {
		json = JsonUtils.stringToJson(HttpRequest.getRequest("https://schoolido.lu/api/cards/?idol_main_unit="+unit+"&rarity="+rarity));
		count = json.getInt("count");
		roll = BotUtils.random.nextInt(count)+1;
		
//		System.out.println(count);
//		System.out.println(roll);
//		System.out.println(Math.round(roll/10));
		
		json = JsonUtils.stringToJson(HttpRequest.getRequest("https://schoolido.lu/api/cards/?idol_main_unit="+unit+"&rarity="+rarity+"&page="+Math.round(roll/10)));
		
		jsonArray = json.getJSONArray("results");
		roll = BotUtils.random.nextInt(jsonArray.length());
		json = jsonArray.getJSONObject(roll);
		c = new Card(json, event.getAuthor().getName());
		BotUtils.sendMessageEmbed(event.getChannel(), c.toEmbed());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    
	});
	
    }
    
    @Override
    public String getName() {
	return "(ll)sif";
    }

}
