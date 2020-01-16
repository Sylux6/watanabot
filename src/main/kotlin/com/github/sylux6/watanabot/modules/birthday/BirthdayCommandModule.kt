package com.github.sylux6.watanabot.modules.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.birthday.commands.GetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.GetCommand
import com.github.sylux6.watanabot.modules.birthday.commands.MonthCommand
import com.github.sylux6.watanabot.modules.birthday.commands.RemoveCommand
import com.github.sylux6.watanabot.modules.birthday.commands.SetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.SetCommand

object BirthdayCommandModule : AbstractCommandModule(
    "Birthday",
    "birthday",
    setOf(
        SetCommand,
        GetCommand, SetChannelCommand,
        GetChannelCommand, RemoveCommand,
        MonthCommand
    )
) {
    override val moduleDescription: String
        get() = "Commands related to members' birthday."
}