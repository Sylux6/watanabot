package db.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.jodatime.date
import org.joda.time.DateTime

object AzurLaneUsers : IntIdTable("azur_lane_users") {
    val userId: Column<Long> = long("user_id").uniqueIndex()
    val oathId: Column<String?> = varchar("oath_id", 128).nullable()
    val oathDate: Column<DateTime?> = date("oath_date").nullable()
}
