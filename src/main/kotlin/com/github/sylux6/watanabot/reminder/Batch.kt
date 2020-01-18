package com.github.sylux6.watanabot.reminder

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.utils.BotUtils
import com.github.sylux6.watanabot.utils.DBUtils
import com.github.sylux6.watanabot.utils.MessageUtils
import org.quartz.Job
import org.quartz.JobExecutionContext
import java.math.BigInteger
import java.time.LocalDate
import java.util.ArrayList
import java.util.Date
import java.util.HashMap

class Batch : Job {

    override fun execute(context: JobExecutionContext) {
        val today = LocalDate.now()
        MessageUtils.sendLog(Date().toString() + " - Running batch")

        // Random status
        BotUtils.randomStatus()

        // Update Azur Lane database
        Atago.reloadDatabase()

        // Birthday
        val l = DBUtils.query(
            "select guildid, userid from member where extract(month from birthday) = " + today.monthValue
                + " and extract(day from birthday) = " + today.dayOfMonth
        )
        if (l.size > 0) {
            val res = HashMap<BigInteger, ArrayList<BigInteger>>()
            // TODO
            for (o in l) {
                o as Array<Any>
                var members: ArrayList<BigInteger>? = res[o[0]]
                if (members == null) {
                    members = ArrayList()
                    res[o[0] as BigInteger] = members
                }
                members.add(o[1] as BigInteger)
            }
            for ((key, value) in res) {
                val settings = DBUtils.query("select birthdaychannelid from settings where guildid = $key")
                val channel = BotUtils.bot.getGuildById(key.toString())!!.getTextChannelById(settings[0].toString())
                if (res.size == 0 || channel == null) {
                    continue
                }
                var found = false
                val wish =
                    StringBuilder("Happy Birthday ${BotUtils.getYousoro(BotUtils.bot.getGuildById(key.toString())!!)} \uD83C\uDF82\n")
                for (id in value) {
                    // FIXME: dirty
                    val user = BotUtils.bot.getUserById(id.toString())
                    if (user != null) {
                        found = true
                        wish.append(MessageUtils.mentionAt(user))
                        wish.append("\n")
                    }
                }
                if (found) {
                    MessageUtils.sendMessage(channel, wish.toString())
                }
            }
        }
    }
}
