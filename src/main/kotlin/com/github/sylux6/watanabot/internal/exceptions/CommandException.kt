package com.github.sylux6.watanabot.internal.exceptions

open class CommandException(message: String? = null) : WatanabotException(message)

class CommandAccessException(message: String? = null) : CommandException(message)