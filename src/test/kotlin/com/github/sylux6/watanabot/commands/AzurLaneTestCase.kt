package com.github.sylux6.watanabot.commands

import com.github.sylux6.watanabot.commands.azur_lane.InfoCommand
import io.kotlintest.shouldBe

class AzurLaneTestCase : CommandsTestCase() {
    init {

        "it should get Atago info"{
            val atago = InfoCommand.runCommand(mockMessageReceivedEvent, listOf("atago"))
            atago.names.en.shouldBe("Atago")
        }
    }
}