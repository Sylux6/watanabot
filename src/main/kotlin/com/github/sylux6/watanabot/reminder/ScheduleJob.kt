package com.github.sylux6.watanabot.reminder

import com.github.sylux6.watanabot.utils.PRIVATE_SERVER_ID
import com.github.sylux6.watanabot.utils.getEmojiMessage
import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.jda
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
                    + getEmojiMessage(jda.getGuildById(PRIVATE_SERVER_ID)!!, "poiBuki")
                    + getYousoro(jda.getGuildById(PRIVATE_SERVER_ID)!!)
            )
            .setImage("https://i.imgur.com/7ODxoKY.png")
            .build()
        sendMessage(
            jda.getGuildById(PRIVATE_SERVER_ID)!!.getTextChannelsByName(
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
                    + getEmojiMessage(jda.getGuildById(PRIVATE_SERVER_ID)!!, "poiBuki")
                    + getYousoro(jda.getGuildById(PRIVATE_SERVER_ID)!!)
            )
            .setImage("https://i.imgur.com/BWyowqe.png")
            .build()
        sendMessage(
            jda.getGuildById(PRIVATE_SERVER_ID)!!.getTextChannelsByName(
                "announcements",
                true
            )[0], m
        )
    }
}
