package modules.azurlane;

import modules.AbstractModule;
import modules.azurlane.entity.Rarity;
import modules.azurlane.entity.Ship;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.BotUtils;
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
                BotUtils.sendMessage(event.getChannel(), toEmbed(ship));
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

    private MessageEmbed toEmbed(Ship ship) {
        return new EmbedBuilder()
                .setTitle(ship.getNameEN() + " ("+ ship.getNameJP() + ")", ship.getUrl())
                .setColor(ship.getRarity().getColor())
                .setThumbnail(ship.getImgIcon())
                .setImage(ship.getImg())
                .setDescription("ID No. " + ship.getId())
                .addField("Class", ship.getShipClass(), true)
                .addField("Nationality", ship.getNationality(), true)
                .addField("Type", ship.getType(), true)
                .addField("Construction time", ship.getConstructionTime(), true)
                .build();
    }
}
