package modules.llsif;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;

import modules.AbstractModule;
import modules.llsif.entity.Card;
import modules.llsif.entity.Idol;
import net.dv8tion.jda.core.entities.User;
import utils.BotUtils;
import utils.HttpRequest;
import utils.JsonUtils;

public class LLModule extends AbstractModule {

    private final String api_card_url = "https://schoolido.lu/api/cards/";
    private final String api_cardid_url = "http://schoolido.lu/api/cardids/";

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
		    if (i.toString().matches("(.*(?i)" + args.get(j) + " .*)")) {
			BotUtils.sendMessage(event.getChannel(), i.toEmbed());
			return;
		    }
		}
	    }
	    BotUtils.sendMessage(event.getChannel(), "Idol not found");
	});

	commands.put("hscout", (event, args) -> {
	    BotUtils.random.setSeed(System.currentTimeMillis());
	    Card c;
	    JSONArray jsonArray;
	    String unit;
	    String rarity;
	    int roll;

	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "u's / aqours argument missing");
		return;
	    }

	    // Main unit scouting
	    switch (args.get(1)) {
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
	    roll = BotUtils.random.nextInt(100) + 1;
	    if (roll == 1) // UR = 1%
		rarity = "UR";
	    else if (roll <= 5) // SSR = 4%
		rarity = "SSR";
	    else if (roll <= 20) // SR = 15%
		rarity = "SR";
	    else // R = 80%
		rarity = "R";

	    try {
		// 1st request
		jsonArray = JsonUtils.stringToJsonArray(
			HttpRequest.getRequest(api_cardid_url, "idol_main_unit=" + unit, "rarity=" + rarity));
				
		c = getCardByID(jsonArray.getInt(BotUtils.random.nextInt(jsonArray.length())), event.getAuthor());

		BotUtils.sendFile(event.getChannel(), c.getFileImg(), "idol.png", c.toEmbedMessage());
	    } catch (Exception e) {
		BotUtils.logger.error("", e);
		BotUtils.sendMessage(event.getChannel(), "Internal error, please retry");
	    }

	});
	
	commands.put("id", (event, args) -> {
	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "ID card missing");
		return;
	    }
	    
	    Card c = getCardByID(Integer.valueOf(args.get(1)), event.getAuthor());
	    
	    if (c == null) {
		BotUtils.sendMessage(event.getChannel(), BotUtils.mentionAt(event.getAuthor()) + " Not found");
	    }
	    else {		
		try {
		    BotUtils.sendFile(event.getChannel(), c.getFileImg(), "idol.png", c.toEmbedMessage());
		} catch (MalformedURLException e) {
		    BotUtils.logger.error("", e);
		} catch (IOException e) {
		    BotUtils.logger.error("", e);
		}
	    }
	});
	
	commands.put("iid", (event, args) -> {
	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "ID card missing");
		return;
	    }
	    
	    Card c = getCardByID(Integer.valueOf(args.get(1)), event.getAuthor());
	    
	    if (c == null) {
		BotUtils.sendMessage(event.getChannel(), BotUtils.mentionAt(event.getAuthor()) + " Not found");
	    }
	    else {		
		try {
		    BotUtils.sendFile(event.getChannel(), c.getFileIdolizedImg(), "idol.png", c.toEmbedMessage());
		} catch (MalformedURLException e) {
		    BotUtils.logger.error("", e);
		} catch (IOException e) {
		    BotUtils.logger.error("", e);
		}
	    }
	});
	
	commands.put("search", (event, args) -> {
	    BotUtils.random.setSeed(System.currentTimeMillis());
	    if (args.size() < 2) {
		BotUtils.sendMessage(event.getChannel(), "search term missing");
		return;
	    }
	    
	    try {
		StringBuilder terms = new StringBuilder();
		for (int i = 1; i < args.size(); i++)
		    terms.append(args.get(i)+" ");
		JSONArray jsonArray = JsonUtils.stringToJsonArray(HttpRequest.getRequest(api_cardid_url, "search=" + terms.toString()));
		if (jsonArray.length() == 0) {
		    BotUtils.sendMessage(event.getChannel(), "No results");
		    return;
		}
		Card c = getCardByID(jsonArray.getInt(BotUtils.random.nextInt(jsonArray.length())), event.getAuthor());
		BotUtils.sendFile(event.getChannel(), c.getFileImg(), "idol.png", c.toEmbedMessage());
	    } catch (Exception e) {
		BotUtils.logger.error("", e);
		BotUtils.sendMessage(event.getChannel(), "Internal error, please retry");
	    }
	});

    }
    
    private Card getCardByID(int id, User user) {
	try {
	    JSONObject json = JsonUtils.stringToJsonObject(HttpRequest.getRequest(api_card_url+"/"+Integer.valueOf(id)));
	    if (json.has("detail"))
		return null;
	    return new Card(json, user);
	} catch (IOException e) {
	    BotUtils.logger.error("", e);
	    return null;
	}
    }

    @Override
    public String getName() {
	return "(ll)sif";
    }

}
