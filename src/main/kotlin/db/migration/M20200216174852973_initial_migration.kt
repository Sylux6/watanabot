package db.migration

import com.improve_future.harmonica.core.AbstractMigration

/**
 * initial_migration
 */
class M20200216174852973_initial_migration : AbstractMigration() {
    override fun up() {
        createTable("settings") {
            bigInteger("guild_id", nullable = false)
            bigInteger("birthday_channel_id", nullable = true)
        }
        createIndex("settings", "guild_id", true)

        createTable("members") {
            date("birthday", nullable = true)
            bigInteger("guild_id", nullable = false)
            bigInteger("user_id", nullable = false)
        }
        createIndex("members", arrayOf("guild_id", "user_id"), true)
    }

    override fun down() {
        dropTable("members")
        dropTable("settings")
    }
}
