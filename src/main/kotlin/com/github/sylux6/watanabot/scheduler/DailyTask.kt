package com.github.sylux6.watanabot.scheduler

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.randomStatus
import com.github.sylux6.watanabot.utils.sendLog
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Members
import db.models.Settings
import java.time.LocalDate
import kotlin.system.measureTimeMillis
import net.dv8tion.jda.api.EmbedBuilder
import org.jetbrains.exposed.sql.JoinType
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
            val birthdays: Map<Pair<Long, Long>, List<Long>> = transaction {
                Members
                    .join(Settings, JoinType.LEFT, onColumn = Members.guildId, otherColumn = Settings.guildId)
                    .slice(Members.guildId, Members.userId, Settings.birthdayChannelId)
                    .select { Members.birthday.month() eq today.monthValue and
                        (Members.birthday.day() eq today.dayOfMonth) }
                    .filter { it[Settings.birthdayChannelId] != null }
                    .groupBy { Pair(it[Members.guildId], it[Settings.birthdayChannelId]!!) }
                    .mapValues { it.value.map { member -> member[Members.userId] } }
            }
            for ((key: Pair<Long, Long>, memberIds: List<Long>) in birthdays) {
                val (guildId: Long, channelId: Long) = key
                val guild = jda.getGuildById(guildId) ?: continue
                val channel = guild.getTextChannelById(channelId) ?: continue
                val mentions =
                    memberIds.mapNotNull { guild.getMemberById(it)?.let { member -> "${mentionAt(member)}\n" } }
                if (mentions.isNotEmpty()) {
                    sendMessage(
                        channel,
                        "Happy Birthday ${getYousoro(jda.getGuildById(key.toString())!!)} \uD83C\uDF82\n" + mentions
                    )
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
