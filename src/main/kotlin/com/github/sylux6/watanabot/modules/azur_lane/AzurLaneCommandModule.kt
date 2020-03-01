package com.github.sylux6.watanabot.modules.azur_lane

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneChibiCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneInfoCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneMarryCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLanePopularityCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneSkinCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneSkinsCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneWaifuCommand

object AzurLaneCommandModule : AbstractCommandModule(
    "Azur Lane",
    "al",
    setOf(
        AzurLaneInfoCommand,
        AzurLaneSkinsCommand,
        AzurLaneSkinCommand,
        AzurLaneChibiCommand,
        AzurLaneMarryCommand,
        AzurLaneWaifuCommand,
        AzurLanePopularityCommand
    )
) {
    override val moduleDescription: String
        get() = "Commands related to Azur Lane."
}
