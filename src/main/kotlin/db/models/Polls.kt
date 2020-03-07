package db.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

object Polls : IntIdTable("polls") {
    val guildId: Column<Long> = long("guild_id")
    val channelId: Column<Long> = long("channel_id")
    val messageId: Column<Long> = long("message_id")
    val authorId: Column<Long> = long("author_id")
    val title: Column<String> = text("title")
    val creationDatetime: Column<DateTime> = datetime("creation_datetime")
    val expirationDatetime: Column<DateTime> = datetime("expiration_datetime")
    val serializedOptions: Column<String> = text("serialized_options")
    val multipleChoices: Column<Boolean> = bool("multiple_choices")

    init {
        index(true, guildId, channelId, messageId)
    }
}
