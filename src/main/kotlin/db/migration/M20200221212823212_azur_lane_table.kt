package db.migration

import com.improve_future.harmonica.core.AbstractMigration
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

/**
 * azur_lane_table
 */
class M20200221212823212_azur_lane_table : AbstractMigration() {
    override fun up() {
        createTable("azur_lane_users") {
            bigInteger("user_id")
            varchar("oath_id", 128, nullable = true)
            date("oath_date", nullable = true)
        }
        createIndex("azur_lane_users", "user_id", true)

        transaction {
            val result = arrayListOf<Pair<Long, String>>()
            exec("SELECT user_id, azur_lane_waifu_id FROM users WHERE azur_lane_waifu_id IS NOT NULL") {
                while (it.next()) {
                    result += it.getLong("user_id") to it.getString("azur_lane_waifu_id")
                }
            }
            val today: String = DateTime.now().toString()
            for (pair in result) {
                val (userId: Long, oathId: String) = pair
                exec("INSERT INTO azur_lane_users (user_id, oath_id, oath_date) VALUES ($userId, '$oathId', '$today')")
            }
        }

        removeColumn("users", "azur_lane_waifu_id")
    }

    override fun down() {
        addVarcharColumn("users", "azur_lane_waifu_id", 128, nullable = true)
        transaction {
            val result = arrayListOf<Pair<Long, String>>()
            exec("SELECT user_id, oath_id FROM azur_lane_users WHERE oath_id IS NOT NULL") {
                while (it.next()) {
                    result += it.getLong("user_id") to it.getString("oath_id")
                }
            }
            for (pair in result) {
                val (userId: Long, oathId: String) = pair
                exec("INSERT INTO users (user_id, azur_lane_waifu_id) VALUES ($userId, '$oathId') " +
                    "ON CONFLICT (user_id) DO UPDATE SET azur_lane_waifu_id = $oathId")
            }
        }
        dropTable("azur_lane_users")
    }
}
