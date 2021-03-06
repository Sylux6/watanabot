package com.github.sylux6.watanabot.internal.exceptions

open class WatanabotException(message: String?) : Exception(message)

fun fail(message: String?): Nothing = throw WatanabotException(message)
