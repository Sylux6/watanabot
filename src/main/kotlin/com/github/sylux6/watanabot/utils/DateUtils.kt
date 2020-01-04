package com.github.sylux6.watanabot.utils

import java.lang.StringBuilder

object DateUtils {

    /**
     * Format a string date returned by SimpleDateFormat
     */
    fun dayFormatter(date: String, hasMonth: Boolean = true): String {
        val day = date.split(" ")
        val result = StringBuilder(
                when(day[0].toInt()) {
                    1 -> "1st"
                    2 -> "2nd"
                    3 -> "3rd"
                    21 -> "21st"
                    31 -> "31st"
                    22 -> "22nd"
                    23 -> "23rd"
                    else -> "${day[0]}th"
                }
        )
        if (hasMonth)
            result.append(" ${day[1]}")
        return result.toString()
    }
}