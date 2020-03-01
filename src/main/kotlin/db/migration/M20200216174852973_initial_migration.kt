package db.migration

import com.improve_future.harmonica.core.AbstractMigration

/**
 * initial_migration
 */
class M20200216174852973_initial_migration : AbstractMigration() {
    override fun up() {
        createTable("guilds") {
            bigInteger("guild_id")
            bigInteger("birthday_channel_id", nullable = true)
        }
        createIndex("guilds", "guild_id", true)

        createTable("users") {
            bigInteger("user_id")
            date("birthday", nullable = true)
        }
        createIndex("users", "user_id", true)
    }

    override fun down() {
        dropTable("users")
        dropTable("guilds")
    }
}
