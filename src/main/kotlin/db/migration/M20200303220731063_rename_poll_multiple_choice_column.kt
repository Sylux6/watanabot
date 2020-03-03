package db.migration

import com.improve_future.harmonica.core.AbstractMigration
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * rename_poll_multiple_choice_column
 */
class M20200303220731063_rename_poll_multiple_choice_column : AbstractMigration() {
    override fun up() {
        transaction {
            exec("ALTER TABLE polls RENAME COLUMN multiple_choice TO multiple_choices")
        }
    }

    override fun down() {
        transaction {
            exec("ALTER TABLE polls RENAME COLUMN multiple_choices TO multiple_choice")
        }
    }
}
