package com.github.sylux6.watanabot.modules.love_live

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveIdCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveIdolizedIdCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveInfoCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveScoutCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveSearchCommand

object LoveLiveCommandModule : AbstractCommandModule(
    "Love Live!",
    "ll",
    setOf(
        LoveLiveInfoCommand,
        LoveLiveScoutCommand,
        LoveLiveIdCommand,
        LoveLiveIdolizedIdCommand,
        LoveLiveSearchCommand
    )
) {
    override val moduleDescription: String
        get() = "Commands related to Love Live!"

    const val CARD_API_URL = "https://schoolido.lu/api/cards/"
    const val CARD_ID_API_URL = "http://schoolido.lu/api/cardids/"
}
