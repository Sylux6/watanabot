package com.github.sylux6.watanabot.modules

import com.github.sylux6.watanabot.modules.azur_lane.commands.InfoCommand
import io.kotlintest.shouldBe

class AzurLaneTestCase : CommandsTestCase() {
    init {
        "should get Atago info" {
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("atago")).id.shouldBe("201")
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("犬")).id.shouldBe("201")
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("愛宕")).id.shouldBe("201")
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("아타고")).id.shouldBe("201")
        }
    }
}