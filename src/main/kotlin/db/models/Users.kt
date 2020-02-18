package db.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.jodatime.date
import org.joda.time.DateTime

object Users : IntIdTable() {
    val userId: Column<Long> = long("user_id").uniqueIndex()
    val birthday: Column<DateTime?> = date("birthday").nullable()
}
