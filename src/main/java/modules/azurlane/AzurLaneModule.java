package modules.azurlane;

import modules.AbstractModule;
import modules.azurlane.entity.Rarity;
import modules.azurlane.entity.Ship;
import net.dv8tion.jda.client.entities.Application;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.BotUtils;
import utils.EmbedUtils;
import utils.HttpRequest;
import utils.JsonUtils;

import java.io.IOException;

public class AzurLaneModule extends AbstractModule {

    @Override
    public void populate() {
        commands.put("info", (event, args) -> {
            if (args.size() < 2)
                return;
            try {
                args.remove(0);
                String ship_name = String.join(" ", args);
                Ship ship = Ship.getShipByName(ship_name);
                if (ship == null) {
                    BotUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                BotUtils.sendMessage(event.getChannel(), ship.toEmbed());
            } catch (Exception e) {
                BotUtils.logger.error("", e);
                BotUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }
        });

        commands.put("skins", (event, args) -> {
            if (args.size() < 2)
                return;
            try {
                args.remove(0);
                String ship_name = String.join(" ", args);
                Ship ship = Ship.getShipByName(ship_name);
                if (ship == null) {
                    BotUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                BotUtils.sendMessage(event.getChannel(), ship.skinListEmbed());
            } catch (Exception e) {
                BotUtils.logger.error("", e);
                BotUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }
        });

        commands.put("skin", (event, args) -> {
            if (args.size() < 3) {
                BotUtils.sendMessage(event.getChannel(),
                        "Index skin is missing, type `o7 al skins` to get the list of skins");
                return;
            }
            try {
                args.remove(0);
                int index = Integer.valueOf(args.get(0));
                args.remove(0);
                String ship_name = String.join(" ", args);
                Ship ship = Ship.getShipByName(ship_name);
                if (ship == null) {
                    BotUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                BotUtils.sendMessage(event.getChannel(), ship.skinEmbed(index));
            } catch (Exception e) {
                BotUtils.logger.error("", e);
                BotUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }
        });

        commands.put("chibi", (event, args) -> {
            if (args.size() < 2)
                return;
            try {
                args.remove(0);
                String ship_name = String.join(" ", args);
                Ship ship = Ship.getShipByName(ship_name);
                if (ship == null) {
                    BotUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                BotUtils.sendMessage(event.getChannel(), ship.chibiEmbed());
            } catch (Exception e) {
                BotUtils.logger.error("", e);
                BotUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }
        });
    }

    @Override
    public String getName() {
        return "azur lane";
    }
}
