package utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class EmbedUtils {

    static public MessageEmbed buildEmbedImageBooru(String title, String url, List<String> tags, String imgURL) {
	EmbedBuilder embedMessage = new EmbedBuilder();
	embedMessage.setTitle(title, url);
	embedMessage.setDescription(String.join(" ", tags));
	embedMessage.setImage(imgURL);
	return embedMessage.build();
    }

    static public MessageEmbed buildEmbedImageOnly(String imgURL) {
		EmbedBuilder embedMessage = new EmbedBuilder();
		embedMessage.addBlankField(false);
		embedMessage.setImage(imgURL);
		return embedMessage.build();
    }

	static public MessageEmbed buildEmbedImageOnly(String title, String url, String imgURL) {
		EmbedBuilder embedMessage = new EmbedBuilder();
		embedMessage.setTitle(title, url);
		embedMessage.addBlankField(false);
		embedMessage.setImage(imgURL);
		return embedMessage.build();
	}

	static public MessageEmbed buildEmbedImageOnly(String title, String imgURL) {
		EmbedBuilder embedMessage = new EmbedBuilder();
		embedMessage.setTitle(title);
		embedMessage.addBlankField(false);
		embedMessage.setImage(imgURL);
		return embedMessage.build();
	}

}
