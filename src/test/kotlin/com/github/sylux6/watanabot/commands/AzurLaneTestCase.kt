package com.github.sylux6.watanabot.commands

import com.github.sylux6.watanabot.commands.azur_lane.InfoCommand
import io.kotlintest.shouldBe

class AzurLaneTestCase : CommandsTestCase() {
    init {

        "it should get Atago info"{
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("atago")).id.shouldBe("201")
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("犬")).id.shouldBe("201")
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("愛宕")).id.shouldBe("201")
            InfoCommand.runCommand(mockMessageReceivedEvent, listOf("아타고")).id.shouldBe("201")
        }
    }
}