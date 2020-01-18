package com.github.sylux6.watanabot.commands.general

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule

object GeneralCommandModule : AbstractCommandModule(
    "General",
    "",
    setOf(
        AvatarCommand, ChooseCommand, GetRoleCommand, HentaiCommand, JoinEventCommand, LeaveEventCommand,
        LewdCommand, NyaCommand, RollCommand, SayCommand, ScreenShareCommand, YousolewdCommand,
        YousoroCommand, ModulesCommand, ServerIconCommand, ShutdownCommand
    )
) {
    override val moduleDescription: String
        get() = "General com.github.sylux6.watanabot.commands."
}