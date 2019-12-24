package commands.general

import internal.commands.AbstractCommandModule

object GeneralCommandModule : AbstractCommandModule(
        "General",
        "",
        setOf(AvatarCommand, ChooseCommand, GetRoleCommand, HentaiCommand, JoinEventCommand, LeaveEventCommand,
                LewdCommand, NyaCommand, RollCommand, SayCommand, ScreenShareCommand, YousolewdCommand,
                YousoroCommand, ModulesCommand, ServerIconCommand)
) {
    override val moduleDescription: String
        get() = "General commands."
}