package com.github.sylux6.watanabot.modules

import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULES
import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULE_MAP
import com.github.sylux6.watanabot.modules.azur_lane.AzurLaneCommandModule
import com.github.sylux6.watanabot.modules.birthday.BirthdayCommandModule
import com.github.sylux6.watanabot.modules.general.GeneralCommandModule
import com.github.sylux6.watanabot.modules.love_live.LoveLiveCommandModule
import com.github.sylux6.watanabot.modules.music.MusicCommandModule
import com.github.sylux6.watanabot.modules.picture.PictureCommandModule
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class CommandModuleTestCase : StringSpec() {
    init {
        "there should not be name conflicts between general commands and module short name" {
            COMMAND_MODULE_MAP.keys.intersect(GeneralCommandModule.commandMap.keys).shouldBeEmpty()
        }

        "it should contain every command module" {
            COMMAND_MODULES.size.shouldBe(5)
            COMMAND_MODULES.shouldContainAll(
                AzurLaneCommandModule,
                BirthdayCommandModule,
                PictureCommandModule,
                LoveLiveCommandModule,
                MusicCommandModule
            )
        }
    }
}