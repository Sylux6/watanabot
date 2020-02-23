package com.github.sylux6.watanabot.internal.types

import com.github.sylux6.watanabot.utils.BOT_PRIMARY_COLOR
import java.awt.Color

enum class BotMessageType(val display: String, val color: Int) {
    NORMAL("", BOT_PRIMARY_COLOR),
    WARNING("⚠️Warning", Color.YELLOW.rgb),
    ERROR("⚠️Error", Color.RED.rgb),
    FATAL("⚠️Fatal error", Color.RED.rgb),
}
