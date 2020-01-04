package com.github.sylux6.watanabot.commands.birthday

import com.github.sylux6.watanabot.internal.commands.AbstractCommand
import com.github.sylux6.watanabot.internal.models.Member
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
                if (!event.member!!.isOwner) {
                    MessageUtils.sendMessage(event.channel, EmbedUtils.buildBotMessage("You cannot set a birthday for another member"))
                    return
                }
                if (event.guild.getMemberById(args.first()) == null) {
                    MessageUtils.sendMessage(event.channel, EmbedUtils.buildBotMessage("Cannot find member id"))
                    return
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
            sendMessageAt(event.channel, event.author,
                    EmbedUtils.buildBotMessage("Cannot get your birthday, please give your birthday following this format: dd/MM"))
        } catch (e: NumberFormatException) {
            sendMessageAt(event.channel, event.author,
                    EmbedUtils.buildBotMessage("Cannot parse the member id"))
        }
    }
}