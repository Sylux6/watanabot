package com.github.sylux6.watanabot.core

import com.github.sylux6.watanabot.commands.azur_lane.AzurLaneCommandModule
import com.github.sylux6.watanabot.commands.birthday.BirthdayCommandModule
import com.github.sylux6.watanabot.commands.love_live.LoveLiveCommandModule
import com.github.sylux6.watanabot.commands.music.MusicCommandModule
import com.github.sylux6.watanabot.commands.picture.PictureCommandModule
import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.utils.NB_THREAD
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object CommandHandler {

    // A map of modules mapping from module name to the map of com.github.sylux6.watanabot.commands
    val COMMAND_MODULE_MAP: MutableMap<String, AbstractCommandModule> = HashMap()

    // Thread pool
    val SERVICE: ExecutorService = Executors.newFixedThreadPool(NB_THREAD)

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
