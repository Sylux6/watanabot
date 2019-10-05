package modules.azurlane;

import modules.AbstractModule;
import modules.azurlane.entity.Ship;
import utils.MessageUtils;

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
                    MessageUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                MessageUtils.sendMessage(event.getChannel(), ship.toEmbed());
            } catch (Exception e) {
                MessageUtils.sendMessage(event.getChannel(), "Internal error, please retry");
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
                    MessageUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                MessageUtils.sendMessage(event.getChannel(), ship.skinListEmbed());
            } catch (Exception e) {
                MessageUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }
        });

        commands.put("skin", (event, args) -> {
            if (args.size() < 3) {
                MessageUtils.sendMessage(event.getChannel(),
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
                    MessageUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                MessageUtils.sendMessage(event.getChannel(), ship.skinEmbed(index));
            } catch (Exception e) {
                MessageUtils.sendMessage(event.getChannel(), "Internal error, please retry");
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
                    MessageUtils.sendMessage(event.getChannel(), "Not found");
                    return;
                }
                MessageUtils.sendMessage(event.getChannel(), ship.chibiEmbed());
            } catch (Exception e) {
                MessageUtils.sendMessage(event.getChannel(), "Internal error, please retry");
            }
        });
    }

    @Override
    public String getName() {
        return "azur lane";
    }
}
