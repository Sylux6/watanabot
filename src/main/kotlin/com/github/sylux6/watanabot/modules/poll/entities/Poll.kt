package com.github.sylux6.watanabot.modules.poll.entities

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import org.joda.time.DateTime

class Poll(
    val author: Member,
    val message: Message,
    val creationDatetime: DateTime,
    val expirationDatetime: DateTime,
    val title: String,
    val options: List<String>,
    val multipleChoices: Boolean
) {
    val hasExpired: () -> Boolean = { !expirationDatetime.isAfterNow }
    // True when the author wants to prematurely close the poll
    var forcedToClose: Boolean = false
}
