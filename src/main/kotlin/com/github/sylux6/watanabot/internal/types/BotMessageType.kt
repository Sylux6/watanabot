package com.github.sylux6.watanabot.internal.types

import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import java.awt.Color

enum class BotMessageType(val display: String, val color: Int) {
    NORMAL("", BOT_PRIMARY_COLOR),
    ERROR("⚠️Error", Color.RED.rgb),
    WARNING("⚠️Warning", Color.YELLOW.rgb)
}
