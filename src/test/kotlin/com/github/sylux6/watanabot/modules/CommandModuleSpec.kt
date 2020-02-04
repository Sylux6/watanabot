package com.github.sylux6.watanabot.modules

import ch.tutteli.atrium.api.fluent.en_GB.contains
import ch.tutteli.atrium.api.fluent.en_GB.inAnyOrder
import ch.tutteli.atrium.api.fluent.en_GB.isEmpty
import ch.tutteli.atrium.api.fluent.en_GB.only
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.fluent.en_GB.values
import ch.tutteli.atrium.api.verbs.expect
import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULES
import com.github.sylux6.watanabot.core.CommandHandler.COMMAND_MODULE_MAP
import com.github.sylux6.watanabot.modules.azur_lane.AzurLaneCommandModule
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneChibiCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneInfoCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneSkinCommand
import com.github.sylux6.watanabot.modules.azur_lane.commands.AzurLaneSkinsCommand
import com.github.sylux6.watanabot.modules.birthday.BirthdayCommandModule
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayGetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayGetCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayMonthCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayRemoveCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdaySetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdaySetCommand
import com.github.sylux6.watanabot.modules.general.GeneralCommandModule
import com.github.sylux6.watanabot.modules.general.commands.AvatarCommand
import com.github.sylux6.watanabot.modules.general.commands.ChooseCommand
import com.github.sylux6.watanabot.modules.general.commands.DebugCommand
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
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveIdCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveIdolizedIdCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveInfoCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveScoutCommand
import com.github.sylux6.watanabot.modules.love_live.commands.LoveLiveSearchCommand
import com.github.sylux6.watanabot.modules.music.MusicCommandModule
import com.github.sylux6.watanabot.modules.music.commands.MusicClearCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicJoinCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicLeaveCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicNextCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicNowCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicPauseCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicPlayCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicPlaylistCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicQueueCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicResumeCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicShuffleCommand
import com.github.sylux6.watanabot.modules.music.commands.MusicStopCommand
import com.github.sylux6.watanabot.modules.picture.PictureCommandModule
import com.github.sylux6.watanabot.modules.picture.commands.PictureNsfwCommand
import com.github.sylux6.watanabot.modules.picture.commands.PictureSafeCommand
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object CommandModuleSpec : Spek({
    describe("Command modules") {
        it("should not have conflicts in general commands and modules name") {
            expect(COMMAND_MODULE_MAP.keys.intersect(GeneralCommandModule.commandMap.keys)).isEmpty()
        }

        it("should not have conflicts in commands name for each module") {
            expect(COMMAND_MODULE_MAP.keys.intersect(GeneralCommandModule.commandMap.keys)).isEmpty()
        }

        it("should define all modules") {
            expect(COMMAND_MODULES.size).toBe(5)
            expect(COMMAND_MODULES).contains.inAnyOrder.only.values(
                AzurLaneCommandModule,
                BirthdayCommandModule,
                PictureCommandModule,
                LoveLiveCommandModule,
                MusicCommandModule
            )
        }

        it("should define all commands") {
            val commandList = COMMAND_MODULES.flatMap { it.commandMap.values } + GeneralCommandModule.commandMap.values
            expect(commandList.size).toBe(52)
            expect(commandList).contains(
                AzurLaneChibiCommand,
                AzurLaneInfoCommand,
                AzurLaneSkinCommand,
                AzurLaneSkinsCommand,
                BirthdayGetChannelCommand,
                BirthdayGetCommand,
                BirthdayMonthCommand,
                BirthdayRemoveCommand,
                BirthdaySetChannelCommand,
                BirthdaySetCommand,
                AvatarCommand,
                ChooseCommand,
                DebugCommand,
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
                LoveLiveIdCommand,
                LoveLiveIdolizedIdCommand,
                LoveLiveInfoCommand,
                LoveLiveScoutCommand,
                LoveLiveSearchCommand,
                MusicClearCommand,
                MusicJoinCommand,
                MusicLeaveCommand,
                MusicNextCommand,
                MusicNowCommand,
                MusicPauseCommand,
                MusicPlayCommand,
                MusicPlaylistCommand,
                MusicQueueCommand,
                MusicResumeCommand,
                MusicShuffleCommand,
                MusicStopCommand,
                PictureNsfwCommand,
                PictureSafeCommand
            )
        }
    }
})
