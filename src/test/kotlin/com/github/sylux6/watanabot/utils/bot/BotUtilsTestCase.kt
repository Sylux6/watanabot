package com.github.sylux6.watanabot.utils.bot

import com.github.sylux6.watanabot.utils.bot.BOT_PREFIX
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BotUtilsTestCase : StringSpec() {
    init {
        "it should be o7" {
            BOT_PREFIX.shouldBe("o7")
        }
    }
}