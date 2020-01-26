package com.github.sylux6.watanabot.reminder

import com.github.sylux6.watanabot.utils.SRID
import com.github.sylux6.watanabot.utils.bot
import com.github.sylux6.watanabot.utils.getEmojiMessage
import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.sendMessage
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
                    + getEmojiMessage(bot.getGuildById(SRID)!!, "poiBuki")
                    + getYousoro(bot.getGuildById(SRID)!!)
            )
            .setImage("https://i.imgur.com/7ODxoKY.png")
            .build()
        sendMessage(
            bot.getGuildById(SRID)!!.getTextChannelsByName(
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
                    + getEmojiMessage(bot.getGuildById(SRID)!!, "poiBuki")
                    + getYousoro(bot.getGuildById(SRID)!!)
            )
            .setImage("https://i.imgur.com/BWyowqe.png")
            .build()
        sendMessage(
            bot.getGuildById(SRID)!!.getTextChannelsByName(
                "announcements",
                true
            )[0], m
        )
    }
}
