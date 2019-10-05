package modules.llsif.entity;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.utils.IOUtil;
import org.json.JSONObject;

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

    public Card(JSONObject json, User user) {
	this.id = json.getInt("id");
	this.name = json.getJSONObject("idol").getString("name");
	this.rarity = Rarity.valueOf(json.getString("rarity"));
	this.attribute = Attribute.valueOf(json.getString("attribute").toUpperCase());
	this.url = json.getString("website_url");
	this.user = user.getName();
	
	if (json.get("card_image").equals(null))
	    this.img = "https:"+json.getString("card_idolized_image");
	else
	    this.img = "https:"+json.getString("card_image");
	this.idolized_img = "https:"+json.getString("card_idolized_image");
	    
	if (json.get("round_card_image").equals(null))
	    this.round_img = "https:"+json.getString("round_card_idolized_image");
	else
	    this.round_img = "https:"+json.getString("round_card_image");
	this.round_idolized_img = "https:"+json.getString("round_card_idolized_image");
	
    }
    
    public Message toEmbedMessage() {
	
	MessageEmbed embed = new EmbedBuilder()
		.setTitle(name + " (ID:"+id+")")
		.setImage("attachment://idol.png")
		.addField("Rarity", rarity.toString(), true)
		.addField("Attribute", attribute.toString(), true)
		.addField("Owner", user, false)
		.setColor(this.attribute==Attribute.COOL?new Color(0, 187, 255):this.attribute==Attribute.PURE?new Color(0, 187, 68):new Color(238, 27, 143))
		.build();
	return new MessageBuilder(embed).build();
    }
    
    public byte[] getFileImg() throws MalformedURLException, IOException {
	return IOUtil.readFully(new URL(this.img).openStream());
    }
    
    public byte[] getFileIdolizedImg() throws MalformedURLException, IOException {
	return IOUtil.readFully(new URL(this.idolized_img).openStream());
    }
    
    public byte[] getFileRoundImg() throws MalformedURLException, IOException {
	return IOUtil.readFully(new URL(this.round_img).openStream());
    }
    
    public byte[] getFileRoundIdolizedImg() throws MalformedURLException, IOException {
	return IOUtil.readFully(new URL(this.round_idolized_img).openStream());
    }
}
