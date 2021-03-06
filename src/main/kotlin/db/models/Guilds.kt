package db.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Guilds : IntIdTable("guilds") {
    val guildId: Column<Long> = long("guild_id").uniqueIndex()
    val birthdayChannelId: Column<Long?> = long("birthday_channel_id").nullable()
}
