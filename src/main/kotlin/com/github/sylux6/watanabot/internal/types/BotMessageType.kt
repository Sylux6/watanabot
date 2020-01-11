package com.github.sylux6.watanabot.internal.types

import com.github.sylux6.watanabot.utils.BotUtils
import java.awt.Color

enum class BotMessageType(val display: String, val color: Int) {
    NORMAL("", BotUtils.PRIMARY_COLOR),
    ERROR("⚠️Error", Color.RED.rgb),
    WARNING("⚠️Warning", Color.YELLOW.rgb)
}