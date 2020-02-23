package com.github.sylux6.watanabot.modules.birthday.commands

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.commands.checkCommandAccess
import com.github.sylux6.watanabot.internal.exceptions.accessFail
import com.github.sylux6.watanabot.internal.exceptions.commandFail
import com.github.sylux6.watanabot.internal.types.CommandLevelAccess
import com.github.sylux6.watanabot.utils.dayFormatter
import com.github.sylux6.watanabot.utils.findMemberOrNull
import com.github.sylux6.watanabot.utils.sendMessageAt
import db.models.Users
import db.utils.insertOrUpdate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object BirthdaySetCommand : AbstractCommand("set", 1) {
    override val template: String
        get() = "[userId] <date>"
    override val description: String
        get() = "Set your birthday or the userId birthday (must be the owner of the server) (format: dd/MM)."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        val formatter = SimpleDateFormat("dd/MM", Locale.ENGLISH)
        try {
            // Check if all conditions are met to set another member birthday
            val (date: DateTime, member: Member) = if (args.size > 1) {
                if (!checkCommandAccess(event, CommandLevelAccess.ADMIN)) {
                    accessFail("You cannot set a birthday for another member")
                }
                DateTime(formatter.parse(args[1])) to (findMemberOrNull(event.guild, args.first())
                    ?: commandFail("Cannot find member"))
            } else {
                DateTime(formatter.parse(args.first())) to event.member!!
            }
            val oldValue: DateTime? = transaction {
                Users
                    .slice(Users.userId, Users.birthday)
                    .select { Users.userId eq member.idLong }
                    .singleOrNull()?.getOrNull(Users.birthday)
            }
            transaction {
                Users.insertOrUpdate(Users.userId) {
                    it[userId] = member.idLong
                    it[birthday] = date
                }
            }
            sendMessageAt(
                event.channel, event.author,
                "${member.effectiveName} birthday is set to the " +
                    dayFormatter(SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date.toDate())) +
                    if (oldValue != null)
                        " (previously ${dayFormatter(SimpleDateFormat("dd MMM", Locale.ENGLISH).format(oldValue.toDate()))})"
                    else
                        ""
            )
        } catch (e: ParseException) {
            commandFail("Cannot get your birthday, please give your birthday following this format: dd/MM")
        } catch (e: NumberFormatException) {
            commandFail("Cannot parse the member id")
        }
    }
}
