package db.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.jodatime.date
import org.joda.time.DateTime

object Members : IntIdTable() {
    val birthday: Column<DateTime?> = date("birthday").nullable()
    val guildId: Column<Long> = long("guild_id")
    val userId: Column<Long> = long("user_id")

    init {
        index(true, guildId, userId)
    }
}
