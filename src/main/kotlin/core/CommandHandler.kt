package core

import commands.azur_lane.AzurLaneCommandModule
import commands.birthday.BirthdayCommandModule
import commands.love_live.LoveLiveCommandModule
import internal.commands.AbstractCommandModule
import commands.music.MusicCommandModule
import commands.picture.PictureCommandModule
import utils.BotUtils.NB_THREAD
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object CommandHandler {

    // A map of modules mapping from module name to the map of commands
    val COMMAND_MODULE_MAP: MutableMap<String, AbstractCommandModule> = HashMap()

    // Thread pool
    val SERVICE: ExecutorService = Executors.newFixedThreadPool(NB_THREAD)

    // Command modules
    val COMMAND_MODULES = setOf(AzurLaneCommandModule, BirthdayCommandModule,
            PictureCommandModule, LoveLiveCommandModule, MusicCommandModule)

    init {
        for (commandModule in COMMAND_MODULES)
            COMMAND_MODULE_MAP[commandModule.shortName] = commandModule
    }
}
