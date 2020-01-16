package com.github.sylux6.watanabot.core

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.azur_lane.AzurLaneCommandModule
import com.github.sylux6.watanabot.modules.birthday.BirthdayCommandModule
import com.github.sylux6.watanabot.modules.love_live.LoveLiveCommandModule
import com.github.sylux6.watanabot.modules.music.MusicCommandModule
import com.github.sylux6.watanabot.modules.picture.PictureCommandModule

object CommandHandler {

    // A map of modules mapping from module name to the map of com.github.sylux6.watanabot.commands
    val COMMAND_MODULE_MAP: MutableMap<String, AbstractCommandModule> = HashMap()

    // Command modules
    val COMMAND_MODULES = setOf(
        AzurLaneCommandModule, BirthdayCommandModule,
        PictureCommandModule, LoveLiveCommandModule, MusicCommandModule
    )

    init {
        for (commandModule in COMMAND_MODULES)
            COMMAND_MODULE_MAP[commandModule.shortName] = commandModule
    }
}
