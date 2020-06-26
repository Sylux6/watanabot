package com.github.sylux6.watanabot.modules.poll.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.modules.poll.utils.sendPollMessage
import java.awt.Color
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.joda.time.DateTime

object PollNew : AbstractCommand("new", 1) {
    override val template: String
        get() = "[--m] [--<duration>] <topic> | <option 1> | <option 2> [ | <option 3> ... ]"
    override val description: String
        get() = "Create a new poll up to 10 options." +
            "\nAdd `--m` flag to allow multiple votes." +
            "\nSpecify poll duration by `--HH` or `HH:mm`"

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val creationDatetime = DateTime.now()
        var expirationDatetime = creationDatetime.plusHours(24)
        var multiple = false

        // Look for optional parameters: hours and multiple
        args.filter { el -> el.startsWith("--") }.forEach { el ->
            val param = el.removePrefix("--")
            when {
                param == "m" -> {
                    multiple = true
                }
                Regex("[0-9]+:[0-9][0-9]").matches(param) -> {
                    val (hours, minutes) = param.split(":").map { it.toInt() }
                    expirationDatetime = creationDatetime.plusHours(hours).plusMinutes(minutes)
                }
                Regex("[0-9]+").matches(param) -> {
                    expirationDatetime = creationDatetime.plusHours(param.toInt())
                }
                else -> {
                    throw CommandException("Invalid parameter: $param")
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
        sendPollMessage(event, embedInit, creationDatetime, expirationDatetime, options.first(), options.drop(1), multiple)
    }
}
