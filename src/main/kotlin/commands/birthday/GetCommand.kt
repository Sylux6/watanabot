package commands.birthday

import internal.commands.AbstractCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.BotUtils.PRIMARY_COLOR
import utils.BotUtils.findMember
import utils.DBUtils.query
import utils.DateUtils.dayFormatter
import utils.MessageUtils.sendMessage
import utils.MessageUtils.sendMessageAt
import java.text.SimpleDateFormat
import java.util.*

object GetCommand : AbstractCommand("get") {
    override val template: String
        get() = "[<member>]"
    override val description: String
        get() = "Get birthday of a member."

    override fun runCommand(event: MessageReceivedEvent, args: List<String>) {
        // No member provided, get self birthday
        if (args.isEmpty()) {
            val res = query("select birthday from member where userid = ${event.author.id} and guildid = ${event.guild.id}")
            if (res.isNotEmpty()) {
                birthdayEmbedMessage(event.channel, event.member!!, res[0] as Date)
            } else {
                sendMessageAt(event.channel, event.author, "You didn't set your birthday")
            }
        } else {
            val username = args.joinToString(" ")
            val member = findMember(event.guild, username)

            if (member == null) {
                sendMessage(event.channel, "Cannot find the user in this server")
                return
            }

            val res = query("select birthday from member where userid = ${member.user.id} and guildid = ${event.guild.id}")
            if (res.isNotEmpty()) {
                birthdayEmbedMessage(event.channel, member, res[0] as Date)
            } else {
                sendMessage(event.channel, "${member.effectiveName} didn't set a birthday")
            }
        }
    }

    private fun birthdayEmbedMessage(channel: MessageChannel, member: Member, date: Date) {
        val message = EmbedBuilder()
                .setColor(PRIMARY_COLOR)
                .setAuthor(member.effectiveName, null, member.user.effectiveAvatarUrl)
                .setDescription("\uD83C\uDF82 ${dayFormatter(SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date))}")
        sendMessage(channel, message.build())
    }
}