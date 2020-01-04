package com.github.sylux6.watanabot.utils

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BotUtilsTestCase : StringSpec() {
    init {
        "it should be o7" {
            BotUtils.BOT_PREFIX.shouldBe("o7")
        }
    }
}