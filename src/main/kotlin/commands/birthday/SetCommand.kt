package commands.birthday

import internal.commands.AbstractCommand
import internal.models.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.DBUtils.query
import utils.DBUtils.saveOrUpdate
import utils.DateUtils.dayFormatter
import utils.MessageUtils.sendMessageAt
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