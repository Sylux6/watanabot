package com.github.sylux6.watanabot.scheduler.jobs

import com.github.sylux6.watanabot.utils.PRIVATE_SERVER_ID
import com.github.sylux6.watanabot.utils.getEmojiMessage
import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.jdaInstance
import com.github.sylux6.watanabot.utils.sendMessage
import net.dv8tion.jda.api.EmbedBuilder
import org.quartz.Job
import org.quartz.JobExecutionContext

class MaiaBirthday : Job {
    override fun execute(context: JobExecutionContext) {
        if (PRIVATE_SERVER_ID == null) {
            return
        }
        val m = EmbedBuilder()
            .setTitle("Birthday")
            .setDescription(
                "TODAY IS MAIA-NEE BIRTHDAY! " +
                    getEmojiMessage(jdaInstance.getGuildById(PRIVATE_SERVER_ID)!!, "poiBuki") +
                    getYousoro(jdaInstance.getGuildById(PRIVATE_SERVER_ID)!!)
            )
            .setImage("https://i.imgur.com/BWyowqe.png")
            .build()
        sendMessage(
            jdaInstance.getGuildById(PRIVATE_SERVER_ID)!!.getTextChannelsByName(
                "announcements",
                true
            )[0], m
        )
    }
}
