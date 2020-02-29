package com.github.sylux6.watanabot.scheduler

import com.github.azurapi.azurapikotlin.api.Atago
import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.log
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.randomStatus
import com.github.sylux6.watanabot.utils.sendLog
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Guilds
import db.models.Users
import java.time.LocalDate
import kotlin.system.measureTimeMillis
import mu.KotlinLogging
import net.dv8tion.jda.api.EmbedBuilder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.jodatime.day
import org.jetbrains.exposed.sql.jodatime.month
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.quartz.Job
import org.quartz.JobExecutionContext

private val logger = KotlinLogging.logger {}

class DailyTask : Job {

    override fun execute(context: JobExecutionContext) {
        val today = LocalDate.now()
        val logBuilder = StringBuilder()
        val embedLogBuilder = EmbedBuilder()
            .setAuthor(jda.selfUser.name, null, jda.selfUser.effectiveAvatarUrl)
            .setColor(BOT_PRIMARY_COLOR)
            .setTitle("Daily task ($today)")

        // Random status
        randomStatus()

        // Update Azur Lane database
        addJob(embedLogBuilder, logBuilder, "Update Azur Lane database") { Atago.reloadDatabase() }

        // Birthday
        addJob(embedLogBuilder, logBuilder, "Check members' birthday") {
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
                    .mapValues { (_, guild) -> guild[Guilds.birthdayChannelId]!! }
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
        logger.log(logBuilder.toString())
        sendLog(embedLogBuilder.build())
    }

    private fun addJob(embedLogBuilder: EmbedBuilder, logBuilder: StringBuilder, jobTitle: String, job: () -> Unit): EmbedBuilder {
        try {
            val timeExecution = measureTimeMillis(job)
            embedLogBuilder.addField(jobTitle, "${timeExecution}ms", false)
            logBuilder.append("$jobTitle executed in ${timeExecution}ms\n")
        } catch (e: Exception) {
            embedLogBuilder.addField(jobTitle, "failed because: ${e.message}", false)
            logBuilder.append("$jobTitle failed because $e\n")
        }
        return embedLogBuilder
    }
}
