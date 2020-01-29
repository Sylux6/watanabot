package com.github.sylux6.watanabot.reminder

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.utils.bot.PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.bot.bot
import com.github.sylux6.watanabot.utils.bot.getYousoro
import com.github.sylux6.watanabot.utils.message.mentionAt
import com.github.sylux6.watanabot.utils.misc.query
import com.github.sylux6.watanabot.utils.bot.randomStatus
import com.github.sylux6.watanabot.utils.message.sendLog
import com.github.sylux6.watanabot.utils.message.sendMessage
import net.dv8tion.jda.api.EmbedBuilder
import org.quartz.Job
import org.quartz.JobExecutionContext
import java.math.BigInteger
import java.time.LocalDate
import java.util.ArrayList
import java.util.HashMap
import kotlin.system.measureTimeMillis

class Batch : Job {

    override fun execute(context: JobExecutionContext) {
        val today = LocalDate.now()
        val messageLog = EmbedBuilder()
            .setAuthor(bot.selfUser.name, null, bot.selfUser.effectiveAvatarUrl)
            .setColor(PRIMARY_COLOR)
            .setTitle("Daily batch ($today)")

        // Random status
        randomStatus()

        // Update Azur Lane database
        addJob(messageLog, "Update Azur Lane database") { Atago.reloadDatabase() }

        // Birthday
        addJob(messageLog, "Check members' birthday") {
            val l = query(
                "select guildid, userid from member where extract(month from birthday) = " + today.monthValue
                    + " and extract(day from birthday) = " + today.dayOfMonth
            )
            if (l.size > 0) {
                val res = HashMap<BigInteger, ArrayList<BigInteger>>()
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
                    val settings =
                        query("select birthdaychannelid from settings where guildid = $key")
                    val channel =
                        bot.getGuildById(key.toString())!!.getTextChannelById(settings[0].toString())
                    if (res.size == 0 || channel == null) {
                        continue
                    }
                    var found = false
                    val wish =
                        StringBuilder("Happy Birthday ${getYousoro(
                            bot.getGuildById(key.toString())!!
                        )} \uD83C\uDF82\n")
                    for (id in value) {
                        // FIXME: dirty
                        val user = bot.getUserById(id.toString())
                        if (user != null) {
                            found = true
                            wish.append(mentionAt(user))
                            wish.append("\n")
                        }
                    }
                    if (found) {
                        sendMessage(
                            channel,
                            wish.toString()
                        )
                    }
                }
            }
        }

        // Send log
        sendLog(messageLog.build())
    }

    private fun addJob(embed: EmbedBuilder, jobTitle: String, job: () -> Unit): EmbedBuilder {
        try {
            embed.addField(jobTitle, "${measureTimeMillis(job)}ms", false)
        } catch (e: Exception) {
            embed.addField(jobTitle, "failed because: ${e.message}", false)
        }
        return embed
    }
}
