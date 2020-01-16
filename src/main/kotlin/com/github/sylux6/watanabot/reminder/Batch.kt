package com.github.sylux6.watanabot.reminder

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.utils.PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.query
import com.github.sylux6.watanabot.utils.randomStatus
import com.github.sylux6.watanabot.utils.sendLog
import com.github.sylux6.watanabot.utils.sendMessage
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
            .setAuthor(jda.selfUser.name, null, jda.selfUser.effectiveAvatarUrl)
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
                    val settings = query("select birthdaychannelid from settings where guildid = $key")
                    val channel =
                        jda.getGuildById(key.toString())!!.getTextChannelById(settings[0].toString())
                    if (res.size == 0 || channel == null) {
                        continue
                    }
                    var found = false
                    val wish =
                        StringBuilder("Happy Birthday ${getYousoro(jda.getGuildById(key.toString())!!)} \uD83C\uDF82\n")
                    for (id in value) {
                        // FIXME: dirty
                        val user = jda.getUserById(id.toString())
                        if (user != null) {
                            found = true
                            wish.append(mentionAt(user))
                            wish.append("\n")
                        }
                    }
                    if (found) {
                        sendMessage(channel, wish.toString())
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
