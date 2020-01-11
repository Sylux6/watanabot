package com.github.sylux6.watanabot.commands.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.commands.checkCommandAccess
import com.github.sylux6.watanabot.internal.exceptions.CommandAccessException
import com.github.sylux6.watanabot.internal.exceptions.CommandException
import com.github.sylux6.watanabot.internal.models.Member
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.github.sylux6.watanabot.utils.DBUtils.query
import com.github.sylux6.watanabot.utils.DBUtils.saveOrUpdate
import com.github.sylux6.watanabot.utils.DateUtils.dayFormatter
import com.github.sylux6.watanabot.utils.EmbedUtils
import com.github.sylux6.watanabot.utils.MessageUtils
import com.github.sylux6.watanabot.utils.MessageUtils.sendMessageAt
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object SetCommand : AbstractCommand("set", 1) {
    override val template: String
        get() = "[userId] <date>"
    override val description: String
        get() = "Set your birthday or the userId birthday (must be the owner of the server) (format: dd/MM)."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val formatter = SimpleDateFormat("dd/MM", Locale.ENGLISH)
        val date: Date
        val member: Member
        try {
            // Check if all conditions are met to set another member birthday
            if (args.size > 1) {
                if (!checkCommandAccess(event, CommandLevelAccess.ADMIN)) {
                    throw CommandAccessException("You cannot set a birthday for another member")
                }
                if (event.guild.getMemberById(args.first()) == null) {
                    throw CommandException("Cannot find member id")
                }
                date = formatter.parse(args[1])
                member = Member(args.first().toLong(), event.guild.idLong, date)
            } else {
                date = formatter.parse(args.first())
                member = Member(event.author.idLong, event.guild.idLong, date)
            }
            val result = query("select id from member where userid = ${event.author.id } and guildid = ${event.guild.id}")
            if (result.isNotEmpty()) {
                member.setId(result[0] as Int)
            }
            saveOrUpdate(member)
            sendMessageAt(event.channel, event.author,
                    "${event.guild.getMemberById(member.getUserId())!!.effectiveName} birthday is set to the "
                            + dayFormatter(SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date)))
        } catch (e: ParseException) {
            throw CommandException("Cannot get your birthday, please give your birthday following this format: dd/MM")
        } catch (e: NumberFormatException) {
            throw CommandException("Cannot parse the member id")
        }
    }
}