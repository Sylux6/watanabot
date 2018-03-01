package modules.llsif.entity;

import org.json.JSONObject;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Card {
    
    private int id;
    private String name;
    private Rarity rarity;
    private Attribute attribute;
    private String url;
    private String img;
    private String idolized_img;
    private String round_img;
    private String round_idolized_img;
    private String user;
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String getIdolized_img() {
        return idolized_img;
    }

    public String getRound_img() {
        return round_img;
    }

    public String getRound_idolized_img() {
        return round_idolized_img;
    }

    public Card(JSONObject json, String user) {
	this.id = json.getInt("id");
	this.name = json.getJSONObject("idol").getString("name");
	this.rarity = Rarity.valueOf(json.getString("rarity"));
	this.attribute = Attribute.valueOf(json.getString("attribute").toUpperCase());
	this.url = json.getString("website_url");
	
	// get a valid url image from kachagain.com using the id
	this.img = "http://kachagain.com/llsif/cards/"+id+".png";	this.user = user;
	
    }
    
    public MessageEmbed toEmbed() {
	return new EmbedBuilder()
		.setTitle(name)
		.setImage(img)
		.addField("Rarity", rarity.toString(), true)
		.addField("Attribute", attribute.toString(), true)
		.addField("Owner", user, false)
		.build();
    }
}
