package db.migration

import com.improve_future.harmonica.core.AbstractMigration
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

/**
 * change_hours_duration_to_expiration_datetime_in_polls_table
 */
class M20200305130305749_change_hours_duration_to_expiration_datetime_in_polls_table : AbstractMigration() {
    override fun up() {
        addDateTimeColumn("polls", "expiration_datetime", default = DateTime.now().toDate(), nullable = false)

        transaction {
            exec("UPDATE polls SET expiration_datetime = creation_datetime + interval '1H' * hours_duration")
        }

        removeColumn("polls", "hours_duration")
    }

    override fun down() {
        removeColumn("polls", "expiration_datetime")
    }
}
