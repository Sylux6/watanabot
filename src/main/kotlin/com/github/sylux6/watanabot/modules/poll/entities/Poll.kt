package com.github.sylux6.watanabot.modules.poll.entities

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import org.joda.time.DateTime

class Poll(
    val author: Member,
    val message: Message,
    val creationDatetime: DateTime,
    val hoursDuration: Int,
    val title: String,
    val options: List<String>,
    val multipleChoices: Boolean
) {
    fun hasExpired(): Boolean {
        return !creationDatetime.plusHours(hoursDuration).isAfterNow
    }
}
