package com.github.sylux6.watanabot.modules

import io.mockk.mockk
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AzurLaneSpec : Spek({
    val mockMessageReceivedEvent: MessageReceivedEvent = mockk(relaxed = true)

    describe("Azur Lane API") {
        // it("should return Atago info") {
        //     expect(AzurLaneInfoCommand.runCommand(mockMessageReceivedEvent, listOf("atago")).id).toBe("201")
        //     expect(AzurLaneInfoCommand.runCommand(mockMessageReceivedEvent, listOf("犬")).id).toBe("201")
        //     expect(AzurLaneInfoCommand.runCommand(mockMessageReceivedEvent, listOf("愛宕")).id).toBe("201")
        //     expect(AzurLaneInfoCommand.runCommand(mockMessageReceivedEvent, listOf("아타고")).id).toBe("201")
        // }
    }
})
