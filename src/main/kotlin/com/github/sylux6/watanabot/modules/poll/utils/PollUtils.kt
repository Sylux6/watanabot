package com.github.sylux6.watanabot.modules.poll.utils

import com.github.sylux6.watanabot.modules.poll.entities.Poll
import com.github.sylux6.watanabot.scheduler.CheckPoll
import com.github.sylux6.watanabot.scheduler.scheduler
import com.github.sylux6.watanabot.utils.deserializeListOfStrings
import com.github.sylux6.watanabot.utils.jda
import com.github.sylux6.watanabot.utils.serializeListOfStrings
import db.models.Polls
import java.awt.Color
import java.util.HashMap
import java.util.Locale
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.GenericMessageEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.quartz.JobBuilder.newJob
import org.quartz.TriggerBuilder

/**
 * Map of polls indexed by <guildId, channelId, messageId>
 */
val pollMap = HashMap<Triple<Long, Long, Long>, Poll>()

fun HashMap<Triple<Long, Long, Long>, Poll>.containsPoll(event: GenericMessageEvent): Boolean {
    return containsKey(Triple(event.guild.idLong, event.channel.idLong, event.messageIdLong))
}

fun HashMap<Triple<Long, Long, Long>, Poll>.getPoll(event: GenericMessageEvent): Poll? {
    return get(Triple(event.guild.idLong, event.channel.idLong, event.messageIdLong))
}

fun HashMap<Triple<Long, Long, Long>, Poll>.removePoll(event: GenericMessageEvent): Poll? {
    return remove(Triple(event.guild.idLong, event.channel.idLong, event.messageIdLong))
}

/**
 * Create or get poll from pollMap and return it
 * @param guildId
 * @param channelId
 * @param messageId
 */
fun getPollOrNull(guildId: Long, channelId: Long, messageId: Long): Poll? {
    return pollMap.getOrDefault(Triple(guildId, channelId, messageId), null)
}

fun savePoll(poll: Poll): Poll {
    transaction {
        Polls
            .insert {
                it[guildId] = poll.message.guild.idLong
                it[channelId] = poll.message.channel.idLong
                it[messageId] = poll.message.idLong
                it[authorId] = poll.author.idLong
                it[title] = poll.title
                it[creationDatetime] = poll.creationDatetime
                it[hoursDuration] = poll.hoursDuration
                it[serializedOptions] = serializeListOfStrings(poll.options)
                it[multipleChoices] = poll.multipleChoices
            }
    }
    pollMap[Triple(poll.message.guild.idLong, poll.message.channel.idLong, poll.message.idLong)] = poll
    return poll
}

/**
 * Send a poll
 */
fun sendPollMessage(
    event: MessageReceivedEvent,
    message: MessageEmbed,
    hoursDuration: Int,
    title: String,
    options: List<String>,
    multipleChoices: Boolean
) {
    event.channel.sendMessage(message).queue() { sentMessage ->
        val poll = savePoll(
            Poll(
                event.member!!,
                sentMessage,
                DateTime.now(),
                hoursDuration,
                title,
                options,
                multipleChoices
            )
        )
        refreshPoll(poll)
        initPoll(poll)
        val trigger = TriggerBuilder
            .newTrigger()
            .startAt(poll.creationDatetime.plusHours(poll.hoursDuration).toDate())
            .build()
        scheduler.scheduleJob(newJob(CheckPoll::class.java).build(), trigger)
    }
}

/**
 * Initialize poll reactions
 */
fun initPoll(poll: Poll) {
    for (index in 1..poll.options.size) {
        poll.message.addReaction(indexToEmote[index] ?: error("")).queue()
    }
}

/**
 * Convert a poll into an embed message
 */
fun refreshPoll(poll: Poll) {
    val embedPoll = EmbedBuilder()
        .setAuthor(poll.author.effectiveName, null, poll.author.user.effectiveAvatarUrl)
        .setTitle(poll.title)
        .setColor(Color.YELLOW)
    if (poll.hasExpired()) {
        embedPoll.setFooter("❌ Closed")
    } else {
        embedPoll.setColor(Color.YELLOW)
        embedPoll.setFooter("✔ Lasting for ${poll.hoursDuration} ${if (poll.hoursDuration > 1) "hours" else "hour"}")
    }
    if (poll.multipleChoices) {
        embedPoll.setDescription("(multiple choices allowed)")
    }

    val votes = HashMap<String, Int>()
    val completableFutures = mutableListOf<CompletableFuture<List<User>>>()

    for (reaction in emoteToIndex.keys) {
        completableFutures.add(poll.message.retrieveReactionUsers(reaction).submit()
            .whenComplete { mutableList, _ -> votes[reaction] = mutableList.filter { !it.isBot }.size }
        )
    }
    CompletableFuture.allOf(*completableFutures.toTypedArray()).thenRun {
        val total = votes.values.reduce { acc, i -> acc + i }
        for ((index, option) in poll.options.withIndex()) {
            val count = votes[indexToEmote[index + 1]] ?: 0
            embedPoll.addField(
                "${indexToEmote[index + 1]} **$option**",
                if (total > 0)
                "${"█".repeat(Math.floorDiv(count * 20, total))} " +
                    "**${String.format(Locale.ENGLISH, " %.1f", count.toDouble() / total * 100)}%** " +
                    "(**$count**)"
                else
                    "**0.0%** (**0**)",
                false
            )
        }
        poll.message.editMessage(embedPoll.build()).queue()
    }
}

/**
 * Retrieve polls from database
 */
fun initPollsFromDb() {
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    transaction {
        Polls
            .selectAll()
            .forEach { row ->
                coroutineScope.launch {
                    val guild = jda.getGuildById(row[Polls.guildId])
                    val channel = jda.getTextChannelById(row[Polls.channelId])
                    val author = guild?.getMemberById(row[Polls.authorId])
                    if (guild != null && channel != null && author != null) {
                        channel.retrieveMessageById(row[Polls.messageId]).queue({ message ->
                            val poll = Poll(
                                author,
                                message,
                                row[Polls.creationDatetime],
                                row[Polls.hoursDuration],
                                row[Polls.title],
                                deserializeListOfStrings(row[Polls.serializedOptions]),
                                row[Polls.multipleChoices]
                            )
                            if (poll.hasExpired()) {
                                removePollFromDatabase(row[Polls.guildId], row[Polls.channelId], row[Polls.messageId])
                            } else {
                                pollMap[Triple(message.guild.idLong, message.channel.idLong, message.idLong)] = poll
                                refreshPoll(poll)
                                val trigger = TriggerBuilder
                                    .newTrigger()
                                    .startAt(row[Polls.creationDatetime].plusHours(row[Polls.hoursDuration]).toDate())
                                    .build()
                                scheduler.scheduleJob(newJob(CheckPoll::class.java).build(), trigger)
                            }
                        }, { _ ->
                            removePollFromDatabase(row[Polls.guildId], row[Polls.channelId], row[Polls.messageId])
                        })
                    } else {
                        removePollFromDatabase(row[Polls.guildId], row[Polls.channelId], row[Polls.messageId])
                    }
                }
            }
    }
}

fun removePollFromDatabase(guildId: Long, channelId: Long, messageId: Long) {
    transaction {
        Polls
            .deleteWhere { Polls.guildId eq guildId and
                (Polls.channelId eq channelId) and
                (Polls.messageId eq messageId)
            }
    }
}

fun removePollFromDatabase(poll: Poll) {
    transaction {
        Polls
            .deleteWhere { Polls.guildId eq poll.message.guild.idLong and
                (Polls.channelId eq poll.message.channel.idLong) and
                (Polls.messageId eq poll.message.idLong)
            }
    }
}
