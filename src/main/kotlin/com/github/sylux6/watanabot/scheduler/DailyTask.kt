package com.github.sylux6.watanabot.scheduler

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.randomStatus
import com.github.sylux6.watanabot.utils.sendLog
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Guilds
import db.models.Users
import java.time.LocalDate
import kotlin.system.measureTimeMillis
import net.dv8tion.jda.api.EmbedBuilder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.jodatime.day
import org.jetbrains.exposed.sql.jodatime.month
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.quartz.Job
import org.quartz.JobExecutionContext

class DailyTask : Job {

    override fun execute(context: JobExecutionContext) {
        val today = LocalDate.now()
        val messageLog = EmbedBuilder()
            .setAuthor(jda.selfUser.name, null, jda.selfUser.effectiveAvatarUrl)
            .setColor(BOT_PRIMARY_COLOR)
            .setTitle("Daily task ($today)")

        // Random status
        randomStatus()

        // Update Azur Lane database
        addJob(messageLog, "Update Azur Lane database") { Atago.reloadDatabase() }

        // Birthday
        addJob(messageLog, "Check members' birthday") {
            val memberIds: List<Long> = transaction {
                Users
                    .slice(Users.userId)
                    .select { Users.birthday.month() eq today.monthValue and (Users.birthday.day() eq today.dayOfMonth) }
                    .map { it[Users.userId] }
            }
            val channelIdsByGuildId: Map<Long, Long> = transaction {
                Guilds
                    .slice(Guilds.guildId, Guilds.birthdayChannelId)
                    .select { Guilds.birthdayChannelId.isNotNull() }
                    .associateBy { it[Guilds.guildId] }
                    .mapValues { (_, value) -> value[Guilds.birthdayChannelId]!! }
            }

            for ((guildId, channelId) in channelIdsByGuildId) {
                val guild = jda.getGuildById(guildId) ?: continue
                val channel = guild.getTextChannelById(channelId) ?: continue
                val mentions = memberIds.mapNotNull { guild.getMemberById(it)?.let { member -> mentionAt(member) } }
                if (mentions.isNotEmpty()) {
                    sendMessage(channel, "Happy Birthday ${getYousoro(guild)} \uD83C\uDF82\n" +
                        mentions.joinToString("\n"))
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
