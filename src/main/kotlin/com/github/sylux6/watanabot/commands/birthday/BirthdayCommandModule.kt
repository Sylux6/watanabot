package com.github.sylux6.watanabot.commands.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule

object BirthdayCommandModule : AbstractCommandModule(
    "Birthday",
    "birthday",
    setOf(SetCommand, GetCommand, SetChannelCommand, GetChannelCommand, RemoveCommand, MonthCommand)
) {
    override val moduleDescription: String
        get() = "Commands related to members' birthday."
}