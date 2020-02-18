package db.migration

import com.improve_future.harmonica.core.AbstractMigration

/**
 * add_azur_lane_waifu_id_column_in_users
 */
class M20200218115958810_add_azur_lane_waifu_id_column_in_users : AbstractMigration() {
    override fun up() {
        addVarcharColumn("users", "azur_lane_waifu_id", 128, nullable = true)
    }

    override fun down() {
        removeColumn("users", "azur_lane_waifu_id")
    }
}
