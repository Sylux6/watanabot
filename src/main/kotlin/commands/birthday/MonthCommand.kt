package commands.birthday

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils.PRIMARY_COLOR
import utils.DBUtils.query
import utils.MessageUtils.sendMessage
import java.sql.Timestamp
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.getInstance

object MonthCommand : AbstractCommand("month") {
    override val template: String
        get() = "[<month>]"
    override val description: String
        get() = "Get birthdays from the current or a given month."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val month = if (args.isEmpty()) LocalDate.now().monthValue else args[0].toInt()

        if (month < 1 || month > 12) {
            sendMessage(event.channel, "Invalid month")
            return
        }

        val l = query("select userid, birthday from member where "
                + "extract(month from birthday) = $month and guildid = ${event.guild.id} order by birthday")

        if (l.isEmpty()) {
            sendMessage(event.channel, "No birthdays this month")
            return
        }

        val result = ArrayList<Pair<Member, Date>>()
        l.forEach { o ->
            o as Array<Any>
            val member = event.guild.getMemberById(o[0].toString())
            if (member != null) {
                result.add(Pair(member, Date((o[1] as Timestamp).time)))
            }
        }
        birthdayInMonthEmbedMessage(event.channel, month, result)
    }

    private fun birthdayInMonthEmbedMessage(channel: MessageChannel, month: Int, members: List<Pair<Member, Date>>) {
        val group = members.groupBy { member -> kotlin.run {
            val calendar: Calendar = getInstance()
            calendar.time = member.second
            calendar.get(DAY_OF_MONTH)
        } }
        val message = EmbedBuilder()
                .setColor(PRIMARY_COLOR)
                .setTitle("Birthdays on ${Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH)}")
        for (groupDay in group) {
            val memberList = StringBuilder()
            for (member in groupDay.value)
                memberList.append("${member.first.effectiveName}\n")
            message.addField(
                    "\uD83C\uDF82 ${groupDay.key}/${DecimalFormat("00").format(month)}",
                    memberList.toString(),
                    false
            )
        }
        sendMessage(channel, message.build())
    }
}
