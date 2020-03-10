package com.github.sylux6.watanabot.scheduler.utils

import com.github.sylux6.watanabot.utils.getYousoro
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.mentionAt
import com.github.sylux6.watanabot.utils.sendMessage
import db.models.Guilds
import db.models.Users
import java.time.LocalDate
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.jodatime.day
import org.jetbrains.exposed.sql.jodatime.month
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Check birthdays in database and send wishes if needed
 */
fun birthdayDailyChecker() {
    val today = LocalDate.now()
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
