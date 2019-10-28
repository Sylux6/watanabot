package commands.azur_lane

import internal.commands.AbstractCommandModule

object AzurLaneCommandModule : AbstractCommandModule(
        "Azur Lane",
        "al",
        setOf(InfoCommand, SkinsCommand, SkinCommand, ChibiCommand)
) {
    override val moduleDescription: String
        get() = "Commands related to Azur Lane."
}