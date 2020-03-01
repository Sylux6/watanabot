package db.migration

import com.improve_future.harmonica.core.AbstractMigration

/**
 * add_polls_table
 */
class M20200224165016464_add_polls_table : AbstractMigration() {
    override fun up() {
        createTable("polls") {
            bigInteger("guild_id", nullable = false)
            bigInteger("channel_id", nullable = false)
            bigInteger("message_id", nullable = false)
            bigInteger("author_id", nullable = false)
            text("title", nullable = false)
            dateTime("creation_datetime", nullable = false)
            integer("hours_duration", nullable = false)
            text("serialized_options", nullable = false)
            boolean("multiple_choice", nullable = false)
        }
        createIndex("polls", arrayOf("guild_id", "channel_id", "message_id"), true)
    }

    override fun down() {
        dropTable("polls")
    }
}
