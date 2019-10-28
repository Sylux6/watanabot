package reminder

import net.dv8tion.jda.api.EmbedBuilder
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import utils.BotUtils
import utils.MessageUtils

class WatanabeYou : Job {

    @Throws(JobExecutionException::class)
    override fun execute(context: JobExecutionContext) {
        val m = EmbedBuilder()
                .setTitle("Birthday")
                .setDescription("TODAY IS MY BIRTHDAY! "
                        + BotUtils.getEmojiMessage(BotUtils.bot.getGuildById(BotUtils.SRID)!!, "poiBuki")
                        + BotUtils.getYousoro(BotUtils.bot.getGuildById(BotUtils.SRID)!!))
                .setImage("https://puu.sh/A3DA1/de62cafe1a.jpg")
                .build()
        MessageUtils.sendMessage(BotUtils.bot.getGuildById(BotUtils.SRID)!!.getTextChannelsByName("announcements", true)[0], m)
    }

}
