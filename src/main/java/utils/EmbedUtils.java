package utils;

import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class EmbedUtils {

    static public MessageEmbed buildEmbedImage(String title, String url, List<String> tags, String imgURL) {
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

}
