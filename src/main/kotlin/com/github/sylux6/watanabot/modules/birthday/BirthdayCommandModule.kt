package com.github.sylux6.watanabot.modules.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommandModule
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayGetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayGetCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayMonthCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdayRemoveCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdaySetChannelCommand
import com.github.sylux6.watanabot.modules.birthday.commands.BirthdaySetCommand

object BirthdayCommandModule : AbstractCommandModule(
    "Birthday",
    "birthday",
    setOf(
        BirthdaySetCommand,
        BirthdayGetCommand,
        BirthdaySetChannelCommand,
        BirthdayGetChannelCommand,
        BirthdayRemoveCommand,
        BirthdayMonthCommand
    )
) {
    override val moduleDescription: String
        get() = "Commands related to members' birthday."
}
