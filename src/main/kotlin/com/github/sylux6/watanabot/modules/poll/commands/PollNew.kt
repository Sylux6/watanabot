package com.github.sylux6.watanabot.modules.poll.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.modules.poll.utils.sendPollMessage
import java.awt.Color
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object PollNew : AbstractCommand("new", 1) {
    override val template: String
        get() = "[--m]  [--<hours>] <topic> | <option 1> | <option 2> [ | <option 3> ... ]"
    override val description: String
        get() = "Create a new poll for 24 hours, up to 10 options. Add `--m` flag to allow multiple votes. "

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        var duration = 24
        var multiple = false

        // Look for optional parameters: hours and multiple
        args.filter { el -> el.startsWith("--") }.forEach { el ->
            when (el.removePrefix("--")) {
                "m" -> multiple = true
                else -> el.removePrefix("--").toIntOrNull()?.let { durationParam ->
                    if (durationParam < 1) {
                        throw CommandException("Invalid hours duration")
                    }
                    duration = durationParam
                }
            }
        }
        val options = args
            .filter { el -> !el.startsWith("--") }
            .joinToString(" ")
            .split("|")
            .map { element -> element.trim() }
        if (options.size < 3 || options.size > 11) {
            throw CommandException("Please provide 2 to 10 options.")
        }
        val embedInit = EmbedBuilder()
            .setAuthor(event.member!!.effectiveName, null, event.author.effectiveAvatarUrl)
            .setDescription("Making poll...")
            .setColor(Color.YELLOW)
            .build()
        sendPollMessage(event, embedInit, duration, options.first(), options.drop(1), multiple)
    }
}
