package utils;

import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;

public class EmbedUtils {
    
    static public EmbedBuilder buildEmbedImage(String title, String url, List<String> tags, String imgURL) {
	EmbedBuilder embedMessage = new EmbedBuilder();
	embedMessage.setTitle(title, url);
	embedMessage.setDescription(String.join(" ", tags));
	embedMessage.setImage(imgURL);
	return embedMessage;
    }

}
