package modules.azurlane.entity;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.HttpRequest;
import utils.JsonUtils;

import java.io.IOException;
import java.util.LinkedHashMap;

public class Ship {

    private String id;
    private String url;
    private String nameEN;
    private String nameJP;
    private String nameCN;
    private String constructionTime;
    private Rarity rarity;
    private String shipClass;
    private String nationality;
    private String type;
    private String img;
    private String imgIcon;
    private String imgChibi;
    private LinkedHashMap<String, String> skins;

    public Ship(String id, String url, String nameEN, String nameJP, String nameCN, String constructionTime, Rarity rarity,
                String shipClass, String nationality, String type, String img, String imgIcon, String imgChibi,
                LinkedHashMap<String, String> skins) {
        this.id = id;
        this.url = url;
        this.nameEN = nameEN;
        this.nameJP = nameJP;
        this.nameCN = nameCN;
        this.constructionTime = constructionTime;
        this.rarity = rarity;
        this.shipClass = shipClass;
        this.nationality = nationality;
        this.type = type;
        this.img = img;
        this.imgIcon = imgIcon;
        this.imgChibi = imgChibi;
        this.skins = skins;
    }

    public String getId() {
        return id;
    }

    public String getUrl() { return url; }

    public String getNameEN() { return nameEN; }

    public String getNameJP() {
        return nameJP;
    }

    public String getNameCN() {
        return nameCN;
    }

    public String getConstructionTime() {
        return constructionTime;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getShipClass() {
        return shipClass;
    }

    public String getNationality() {
        return nationality;
    }

    public String getType() {
        return type;
    }

    public String getImg() {
        return img;
    }

    public String getImgIcon() {
        return imgIcon;
    }

    public String getImgChibi() {
        return imgChibi;
    }

    public LinkedHashMap<String, String> getSkins() {
        return skins;
    }

    static public Rarity getRarityByName(String rarity) {
        switch (rarity) {
            case "Super Rare": return Rarity.SUPER_RARE;
            case "Elite": return Rarity.ELITE;
            case "Rare": return Rarity.RARE;
            case "Normal": return Rarity.NORMAL;
            default: return Rarity.UNKNOWN;
        }
    }

    public MessageEmbed toEmbed() {
        return new EmbedBuilder()
                .setTitle(this.getNameEN() + " ("+ this.getNameJP() + ")", this.getUrl())
                .setColor(this.getRarity().getColor())
                .setThumbnail(this.getImgIcon())
                .setImage(this.getImg())
                .setDescription("ID No. " + this.getId())
                .addField("Class", this.getShipClass(), true)
                .addField("Nationality", this.getNationality(), true)
                .addField("Type", this.getType(), true)
                .addField("Construction time", this.getConstructionTime(), true)
                .build();
    }

    public MessageEmbed skinEmbed(int i) {
        if (i == 0 || i > this.skins.size() || i < 0) {
            return new EmbedBuilder().setDescription("No skin found").build();
        } else {
            String name = "", skin = "";
            for (String key : skins.keySet()) {
                name = key;
                img = this.skins.get(key);
                if (--i == 0) break;
            }
            return new EmbedBuilder()
                    .setTitle(this.getNameEN() + " (" + this.getNameJP() + ")", this.getUrl())
                    .setColor(this.getRarity().getColor())
                    .setDescription(name)
                    .setImage(img)
                    .build();
        }
    }

    public MessageEmbed skinListEmbed() {
        EmbedBuilder embedShip = new EmbedBuilder()
                .setTitle(this.getNameEN() + " ("+ this.getNameJP() + ")", this.getUrl())
                .setColor(this.getRarity().getColor())
                .setThumbnail(this.getImgIcon());
        if (skins.size() > 0) {
            int i = 1;
            StringBuilder list = new StringBuilder();
            for (String key : skins.keySet()) {
                list.append(i + ". " + key + "\n");
                i++;
            }
            embedShip.addField("Skin(s)", list.toString(), true);
        } else {
            embedShip.setDescription("No skin");
        }
        return embedShip.build();
    }

    public MessageEmbed chibiEmbed() {
        return new EmbedBuilder()
                .setTitle(this.getNameEN() + " ("+ this.getNameJP() + ")", this.getUrl())
                .setColor(this.getRarity().getColor())
                .setImage(this.imgChibi)
                .build();

    }

    static public Ship getShipByName(String name) throws IOException {
        JSONObject jsonObject = JsonUtils.stringToJsonObject(HttpRequest.getRequest(
                "https://api.kurozeropb.info/v1/azurlane/ship", "name=" + name
        ));
        if (jsonObject.getInt("statusCode") != 200) {
            return null;
        }
        JSONObject ship = jsonObject.getJSONObject("ship");
        JSONArray skins = ship.getJSONArray("skins");
        LinkedHashMap<String, String> skinMap = new LinkedHashMap<>();
        for (Object skin : skins) {
            skinMap.put(((JSONObject) skin).getString("title"), ((JSONObject) skin).getString("image"));
        }
        String id = ship.getString("id");
        String url = ship.getString("wikiUrl");
        String nameEN = JsonUtils.getStringOrNull(ship.getJSONObject("names"), "en");
        String nameJP = JsonUtils.getStringOrNull(ship.getJSONObject("names"), "jp");
        String nameCN = JsonUtils.getStringOrNull(ship.getJSONObject("names"), "cn");
        String constructionTime = JsonUtils.getStringOrNull(ship, "buildTime");
        Rarity rarity = getRarityByName(JsonUtils.getStringOrNull(ship, "rarity"));
        String shipClass = JsonUtils.getStringOrNull(ship, "class");
        String nationality = JsonUtils.getStringOrNull(ship, "nationality");
        String type = JsonUtils.getStringOrNull(ship, "hullType");
        String img = skinMap.get("Default");
        String imgIcon = JsonUtils.getStringOrNull(ship, "thumbnail");
        String imgChibi = JsonUtils.getStringOrNull(ship, "chibi");
        skinMap.remove("Default");
        return new Ship(id, url, nameEN, nameJP, nameCN, constructionTime, rarity, shipClass, nationality, type, img,
                imgIcon, imgChibi, skinMap);
    }
}
