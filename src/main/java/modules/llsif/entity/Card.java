package modules.llsif.entity;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.IOUtil;

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
	
	this.img = "https:"+json.getString("card_image");
	this.idolized_img = json.getString("card_idolized_image");
	System.out.println(this.img);
	// get a valid url image from kachagain.com using the id
//	this.img = "http://kachagain.com/llsif/cards/"+id+".png";	
//	if (json.get("card_image").equals(null))
//	    this.idolized_img = this.img;
//	else 
//	    this.idolized_img = "http://kachagain.com/llsif/cards/"+id+"_t.png";
	this.user = user.getName();
	
    }
    
    public void toEmbed(boolean idolized, MessageChannel chan) {
	try {
	    byte[] file = IOUtil.readFully(new URL(this.img).openStream());
	    MessageEmbed embed = new EmbedBuilder()
		    .setTitle(name + " (ID:"+id+")")
		    .setImage("attachment://idol.png")
		    .addField("Rarity", rarity.toString(), true)
		    .addField("Attribute", attribute.toString(), true)
		    .addField("Owner", user, false)
		    .setColor(this.attribute==Attribute.COOL?new Color(0, 187, 255):this.attribute==Attribute.PURE?new Color(0, 187, 68):new Color(238, 27, 143))
		    .build();
	    MessageBuilder m = new MessageBuilder();
	    m.setEmbed(embed);
	    chan.sendFile(file, "idol.png", m.build()).queue();
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
