package com.github.sylux6.watanabot.reminder

import com.github.sylux6.watanabot.utils.BotUtils
import com.github.sylux6.watanabot.utils.MessageUtils
import net.dv8tion.jda.api.EmbedBuilder
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

class WatanabeYou : Job {

    @Throws(JobExecutionException::class)
    override fun execute(context: JobExecutionContext) {
        val m = EmbedBuilder()
            .setTitle("Birthday")
            .setDescription(
                "TODAY IS MY BIRTHDAY! "
                    + BotUtils.getEmojiMessage(BotUtils.bot.getGuildById(BotUtils.SRID)!!, "poiBuki")
                    + BotUtils.getYousoro(BotUtils.bot.getGuildById(BotUtils.SRID)!!)
            )
            .setImage("https://i.imgur.com/7ODxoKY.png")
            .build()
        MessageUtils.sendMessage(
            BotUtils.bot.getGuildById(BotUtils.SRID)!!.getTextChannelsByName(
                "announcements",
                true
            )[0], m
        )
    }
}

class Maia : Job {

    @Throws(JobExecutionException::class)
    override fun execute(context: JobExecutionContext) {
        val m = EmbedBuilder()
            .setTitle("Birthday")
            .setDescription(
                "TODAY IS MAIA-NEE BIRTHDAY! "
                    + BotUtils.getEmojiMessage(BotUtils.bot.getGuildById(BotUtils.SRID)!!, "poiBuki")
                    + BotUtils.getYousoro(BotUtils.bot.getGuildById(BotUtils.SRID)!!)
            )
            .setImage("https://i.imgur.com/BWyowqe.png")
            .build()
        MessageUtils.sendMessage(
            BotUtils.bot.getGuildById(BotUtils.SRID)!!.getTextChannelsByName(
                "announcements",
                true
            )[0], m
        )
    }
}
