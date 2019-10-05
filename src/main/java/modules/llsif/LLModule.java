package modules.llsif;

import modules.AbstractModule;
import modules.llsif.entity.Card;
import modules.llsif.entity.Idol;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.BotUtils;
import utils.HttpRequest;
import utils.JsonUtils;
import utils.MessageUtils;

import java.io.IOException;
import java.net.MalformedURLException;

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
                        MessageUtils.sendMessage(event.getChannel(), i.toEmbed());
                        return;
                    }
                }
            }
            MessageUtils.sendMessage(event.getChannel(), "Idol not found");
        });

        commands.put("hscout", (event, args) -> {
            BotUtils.random.setSeed(System.currentTimeMillis());
            Card c;
            JSONArray jsonArray;
            String unit;
            String rarity;
            int roll;

            if (args.size() < 2) {
                MessageUtils.sendMessage(event.getChannel(), "u's / aqours argument missing");
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
                    MessageUtils.sendMessage(event.getChannel(), "u's / aqours argument missing");
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

                MessageUtils.sendFile(event.getChannel(), c.getFileImg(), "idol.png", c.toEmbedMessage());
            } catch (Exception e) {
                MessageUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }

        });

        commands.put("id", (event, args) -> {
            if (args.size() < 2) {
                MessageUtils.sendMessage(event.getChannel(), "ID card missing");
                return;
            }

            Card c = getCardByID(Integer.valueOf(args.get(1)), event.getAuthor());

            if (c == null) {
                MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " Not found");
            } else {
                try {
                    MessageUtils.sendFile(event.getChannel(), c.getFileImg(), "idol.png", c.toEmbedMessage());
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }
            }
        });

        commands.put("iid", (event, args) -> {
            if (args.size() < 2) {
                MessageUtils.sendMessage(event.getChannel(), "ID card missing");
                return;
            }

            Card c = getCardByID(Integer.valueOf(args.get(1)), event.getAuthor());

            if (c == null) {
                MessageUtils.sendMessage(event.getChannel(), MessageUtils.mentionAt(event.getAuthor()) + " Not found");
            } else {
                try {
                    MessageUtils.sendFile(event.getChannel(), c.getFileIdolizedImg(), "idol.png", c.toEmbedMessage());
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }
            }
        });

        commands.put("search", (event, args) -> {
            BotUtils.random.setSeed(System.currentTimeMillis());
            if (args.size() < 2) {
                MessageUtils.sendMessage(event.getChannel(), "search term missing");
                return;
            }

            try {
                args.remove(0);
                JSONArray jsonArray = JsonUtils.stringToJsonArray(HttpRequest.getRequest(api_cardid_url, "search=" + String.join(" ", args)));
                if (jsonArray.length() == 0) {
                    MessageUtils.sendMessage(event.getChannel(), "No results");
                    return;
                }
                Card c = getCardByID(jsonArray.getInt(BotUtils.random.nextInt(jsonArray.length())), event.getAuthor());
                MessageUtils.sendFile(event.getChannel(), c.getFileImg(), "idol.png", c.toEmbedMessage());
            } catch (Exception e) {
                MessageUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }
        });

    }

    private Card getCardByID(int id, User user) {
        try {
            JSONObject json = JsonUtils.stringToJsonObject(HttpRequest.getRequest(api_card_url + "/" + Integer.valueOf(id)));
            if (json.has("detail"))
                return null;
            return new Card(json, user);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getName() {
        return "(ll)sif";
    }

}
