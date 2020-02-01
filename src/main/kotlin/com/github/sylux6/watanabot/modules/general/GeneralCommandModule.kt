package com.github.sylux6.watanabot.modules.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
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

object GeneralCommandModule : AbstractCommandModule(
    "General",
    "",
    setOf(
        AvatarCommand,
        ChooseCommand,
        GetRoleCommand,
        HentaiCommand,
        JoinEventCommand,
        LeaveEventCommand,
        LewdCommand,
        NyaCommand,
        RollCommand,
        SayCommand,
        ScreenShareCommand,
        YousolewdCommand,
        YousoroCommand,
        ModulesCommand,
        ServerIconCommand,
        ShutdownCommand
    )
) {
    override val moduleDescription: String
        get() = "General com.github.sylux6.watanabot.commands."
}