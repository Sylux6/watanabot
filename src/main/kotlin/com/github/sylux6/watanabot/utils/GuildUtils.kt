package com.github.sylux6.watanabot.utils

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.exceptions.ErrorResponseException

fun Guild.retrieveMemberByIdOrNull(id: Long): Member? {
    return try {
        retrieveMemberById(id).complete()
    } catch (e: ErrorResponseException) {
        if (e.errorResponse.name == ("UNKNOWN_MEMBER")) {
            null
        } else {
            throw e
        }
    }
}

fun Guild.retrieveMemberByIdOrNull(id: String): Member? {
    return try {
        retrieveMemberById(id).complete()
    } catch (e: ErrorResponseException) {
        if (e.errorResponse.name == ("UNKNOWN_MEMBER")) {
            null
        } else {
            throw e
        }
    }
}
