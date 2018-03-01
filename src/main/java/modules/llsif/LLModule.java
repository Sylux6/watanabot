package modules.llsif;

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

    private final String api_card_url = "https://schoolido.lu/api/cards/";

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
	    JSONObject json;
	    JSONArray jsonArray;
	    String unit;
	    String rarity;
	    int roll;
	    int count = 0;

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
		json = JsonUtils.stringToJson(
			HttpRequest.getRequest(api_card_url, "idol_main_unit=" + unit, "rarity=" + rarity));

		// get number of results & rolling
		count = json.getInt("count");
		roll = BotUtils.random.nextInt(count) + 1;

		// get the right number page
		json = JsonUtils.stringToJson(HttpRequest.getRequest(api_card_url, "idol_main_unit=" + unit,
			"rarity=" + rarity, "page=" + (int) Math.ceil((double) roll / 10)));

		// take the roll-th card
		jsonArray = json.getJSONArray("results");
		json = jsonArray.getJSONObject(roll - (((int) Math.ceil((double) roll / 10)) - 1) * 10);

		// build card instance
		c = new Card(json, event.getAuthor().getName());

		// send result
		BotUtils.sendMessage(event.getChannel(), c.toEmbed());
	    } catch (IOException e) {
		e.printStackTrace();
		BotUtils.sendMessage(event.getChannel(), "Internal error, please retry");
	    }

	});

    }

    @Override
    public String getName() {
	return "(ll)sif";
    }

}
