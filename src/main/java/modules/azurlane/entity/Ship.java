package modules.azurlane.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.BotUtils;
import utils.HttpRequest;
import utils.JsonUtils;

import java.io.IOException;

public class Ship {

    private int id;
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

    public Ship(int id, String url, String nameEN, String nameJP, String nameCN, String constructionTime, Rarity rarity,
                String shipClass, String nationality, String type, String img, String imgIcon, String imgChibi) {
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
    }

    public int getId() {
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

    static public Rarity getRarityByName(String rarity) {
        switch (rarity) {
            case "Super Rare": return Rarity.SUPER_RARE;
            case "Elite": return Rarity.ELITE;
            case "Rare": return Rarity.RARE;
            case "Normal": return Rarity.NORMAL;
            default: return Rarity.UNKNOWN;
        }
    }

    static public Ship getShipByName(String name) throws IOException {
        JSONArray jsonArray = JsonUtils.stringToJsonArray(HttpRequest.getRequest("https://al-shipgirls" +
                ".pw/shipyard/ship_info_detailed", "search=" + name));
        if (jsonArray.length() < 1) {
            return null;
        }
        JSONObject object = jsonArray.getJSONObject(0).getJSONObject("item");
        int id = object.getInt("id");
        String url = object.getString("page_url");
        String nameEN = object.getJSONObject("names").getString("en");
        String nameJP = object.getJSONObject("names").getString("jp");
        String nameCN = object.getJSONObject("names").getString("cn");
        String constructionTime = object.getString("construction_time");
        Rarity rarity = getRarityByName(object.getString("rarity"));
        String shipClass = object.getString("class");
        String nationality = object.getString("nationality");
        String type = object.getString("type");
        String img = object.getJSONArray("images").getJSONObject(0).getString("url");
        String imgIcon = object.getString("icon");
        String imgChibi = object.getString("chibi");
        return new Ship(id, url, nameEN, nameJP, nameCN, constructionTime, rarity, shipClass, nationality, type, img,
                imgIcon, imgChibi);
    }
}
