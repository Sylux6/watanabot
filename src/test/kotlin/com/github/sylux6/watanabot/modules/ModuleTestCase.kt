package com.github.sylux6.watanabot.modules

import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULES
import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULE_MAP
import com.github.sylux6.watanabot.modules.azur_lane.AzurLaneCommandModule
import com.github.sylux6.watanabot.modules.azur_lane.commands.ChibiCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.InfoCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.SkinCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.SkinsCommand
import com.github.sylux6.watanabot.modules.birthday.BirthdayCommandModule
import com.github.sylux6.watanabot.modules.birthday.commands.GetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.GetCommand
import com.github.sylux6.watanabot.modules.birthday.commands.MonthCommand
import com.github.sylux6.watanabot.modules.birthday.commands.RemoveCommand
import com.github.sylux6.watanabot.modules.birthday.commands.SetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.SetCommand
import com.github.sylux6.watanabot.modules.general.GeneralCommandModule
import com.github.sylux6.watanabot.modules.general.commands.AvatarCommand
import com.github.sylux6.watanabot.modules.general.commands.ChooseCommand
import com.github.sylux6.watanabot.modules.general.commands.GetRoleCommand
import com.github.sylux6.watanabot.modules.general.commands.HentaiCommand
import com.github.sylux6.watanabot.modules.general.commands.JoinEventCommand
import com.github.sylux6.watanabot.modules.general.commands.LeaveEventCommand
import com.github.sylux6.watanabot.modules.general.commands.LewdCommand
import com.github.sylux6.watanabot.modules.general.commands.ModulesCommand
import com.github.sylux6.watanabot.modules.general.commands.NyaCommand
import com.github.sylux6.watanabot.modules.general.commands.RollCommand
import com.github.sylux6.watanabot.modules.general.commands.SayCommand
import com.github.sylux6.watanabot.modules.general.commands.ScreenShareCommand
import com.github.sylux6.watanabot.modules.general.commands.ServerIconCommand
import com.github.sylux6.watanabot.modules.general.commands.ShutdownCommand
import com.github.sylux6.watanabot.modules.general.commands.YousolewdCommand
import com.github.sylux6.watanabot.modules.general.commands.YousoroCommand
import com.github.sylux6.watanabot.modules.love_live.LoveLiveCommandModule
import com.github.sylux6.watanabot.modules.love_live.commands.IdCommand
import com.github.sylux6.watanabot.modules.love_live.commands.IdolizedIdCommand
import com.github.sylux6.watanabot.modules.love_live.commands.ScoutCommand
import com.github.sylux6.watanabot.modules.love_live.commands.SearchCommand
import com.github.sylux6.watanabot.modules.music.MusicCommandModule
import com.github.sylux6.watanabot.modules.music.commands.ClearCommand
import com.github.sylux6.watanabot.modules.music.commands.JoinCommand
import com.github.sylux6.watanabot.modules.music.commands.LeaveCommand
import com.github.sylux6.watanabot.modules.music.commands.NextCommand
import com.github.sylux6.watanabot.modules.music.commands.NowCommand
import com.github.sylux6.watanabot.modules.music.commands.PauseCommand
import com.github.sylux6.watanabot.modules.music.commands.PlayCommand
import com.github.sylux6.watanabot.modules.music.commands.PlaylistCommand
import com.github.sylux6.watanabot.modules.music.commands.QueueCommand
import com.github.sylux6.watanabot.modules.music.commands.ResumeCommand
import com.github.sylux6.watanabot.modules.music.commands.ShuffleCommand
import com.github.sylux6.watanabot.modules.music.commands.StopCommand
import com.github.sylux6.watanabot.modules.picture.PictureCommandModule
import com.github.sylux6.watanabot.modules.picture.commands.NsfwCommand
import com.github.sylux6.watanabot.modules.picture.commands.SafeCommand
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldBeUnique
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class CommandModuleTestCase : StringSpec() {
    init {
        "should not have conflicts in general commands and modules name" {
            COMMAND_MODULE_MAP.keys.intersect(GeneralCommandModule.commandMap.keys).shouldBeEmpty()
        }

        "should not have conflicts in commands name for each module" {
            GeneralCommandModule.commands.map { it.name }.shouldBeUnique()
            for (module in COMMAND_MODULES) {
                module.commands.map { it.name }.shouldBeUnique()
            }
        }

        "should define all modules" {
            COMMAND_MODULES.size.shouldBe(5)
            COMMAND_MODULES.shouldContainAll(
                AzurLaneCommandModule,
                BirthdayCommandModule,
                PictureCommandModule,
                LoveLiveCommandModule,
                MusicCommandModule
            )
        }

        "should define all commands" {
            val commandList = COMMAND_MODULES.flatMap { it.commandMap.values } + GeneralCommandModule.commandMap.values
            commandList.size.shouldBe(51)
            commandList.shouldContainAll(
                ChibiCommand,
                InfoCommand,
                SkinCommand,
                SkinsCommand,
                GetChannelCommand,
                GetCommand,
                MonthCommand,
                RemoveCommand,
                SetChannelCommand,
                SetCommand,
                AvatarCommand,
                ChooseCommand,
                GetRoleCommand,
                HentaiCommand,
                JoinEventCommand,
                LeaveEventCommand,
                LewdCommand,
                ModulesCommand,
                NyaCommand,
                RollCommand,
                SayCommand,
                ScreenShareCommand,
                ServerIconCommand,
                ShutdownCommand,
                YousolewdCommand,
                YousoroCommand,
                IdCommand,
                IdolizedIdCommand,
                com.github.sylux6.watanabot.modules.love_live.commands.InfoCommand,
                ScoutCommand,
                SearchCommand,
                ClearCommand,
                JoinCommand,
                LeaveCommand,
                NextCommand,
                NowCommand,
                PauseCommand,
                PlayCommand,
                PlaylistCommand,
                QueueCommand,
                ResumeCommand,
                ShuffleCommand,
                StopCommand,
                NsfwCommand,
                SafeCommand
            )
        }
    }
}