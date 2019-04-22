package reminder;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import utils.BotUtils;

public class WatanabeYou implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
	MessageEmbed m = new EmbedBuilder()
		    .setTitle("Birthday")
		    .setDescription("TODAY IS MY BIRTHDAY! "
			    +BotUtils.getEmojiMessage(BotUtils.bot.getGuildById(BotUtils.SRID), "poiBuki")
			    +BotUtils.getYousoro(BotUtils.bot.getGuildById(BotUtils.SRID)))
		    .setImage("https://puu.sh/A3DA1/de62cafe1a.jpg")
		    .build();
	BotUtils.sendMessage(BotUtils.bot.getGuildById(BotUtils.SRID).getTextChannelsByName("announcements", true).get(0), m);
    }

}
