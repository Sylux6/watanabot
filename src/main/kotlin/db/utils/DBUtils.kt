package db.utils

import com.github.sylux6.watanabot.utils.CONFIG_DB_HOST
import com.github.sylux6.watanabot.utils.CONFIG_DB_NAME
import com.github.sylux6.watanabot.utils.CONFIG_DB_PASSWORD
import com.github.sylux6.watanabot.utils.CONFIG_DB_PORT
import com.github.sylux6.watanabot.utils.CONFIG_DB_USER
import com.github.sylux6.watanabot.utils.config
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.TransactionManager

/**
 * Static function to connect to database (useful during migration)
 */
fun connectToDatabase() {
    Database.connect(
        "jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME",
        driver = "org.postgresql.Driver",
        user = DB_USER,
        password = DB_PASSWORD
    )
}

// Configuration
val DB_HOST = config[CONFIG_DB_HOST]
val DB_PORT = config[CONFIG_DB_PORT]
val DB_NAME = config[CONFIG_DB_NAME]
val DB_USER = config[CONFIG_DB_USER]
val DB_PASSWORD = config[CONFIG_DB_PASSWORD]

// INSERT_OR_UPDATE implementation
fun <T : Table> T.insertOrUpdate(vararg keys: Column<*>, body: T.(InsertStatement<Number>) -> Unit) =
    InsertOrUpdate<Number>(keys, this).apply {
        body(this)
        execute(TransactionManager.current())
    }

class InsertOrUpdate<Key : Any>(
    private val keys: Array<out Column<*>>,
    table: Table,
    isIgnore: Boolean = false
) : InsertStatement<Key>(table, isIgnore) {
    override fun prepareSQL(transaction: Transaction): String {
        val updateSetter = super.values.keys.joinToString { "${it.name} = EXCLUDED.${it.name}" }
        val keyColumns = keys.joinToString(",") { it.name }
        val onConflict = "ON CONFLICT ($keyColumns) DO UPDATE SET $updateSetter"
        return "${super.prepareSQL(transaction)} $onConflict"
    }
}
