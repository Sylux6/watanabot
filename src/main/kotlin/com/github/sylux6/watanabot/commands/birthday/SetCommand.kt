package com.github.sylux6.watanabot.commands.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.models.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.DBUtils.query
import com.github.sylux6.watanabot.utils.DBUtils.saveOrUpdate
import com.github.sylux6.watanabot.utils.DateUtils.dayFormatter
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessageAt
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object SetCommand : AbstractCommand("set", 1) {
    override val template: String
        get() = "<date>"
    override val description: String
        get() = "Set your birthday (format: dd/MM)."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val formatter = SimpleDateFormat("dd/MM", Locale.ENGLISH)
        try {
            val date = formatter.parse(args[0])
            val member = Member(event.author.idLong, event.guild.idLong, date)
            val result = query("select id from member where userid = ${event.author.id } and guildid = ${event.guild.id}")
            if (result.isNotEmpty()) {
                member.setId(result[0] as Int)
            }
            saveOrUpdate(member)
            sendMessageAt(event.channel, event.author,
                    "Your birthday is set to the "
                            + dayFormatter(SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date)))
        } catch (e: ParseException) {
            sendMessageAt(event.channel, event.author,
                    "Cannot get your birthday, please give your birthday following this format: dd/MM")
        }
    }
}