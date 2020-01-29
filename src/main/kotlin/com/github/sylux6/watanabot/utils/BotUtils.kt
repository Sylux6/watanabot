package com.github.sylux6.watanabot.utils

import info.debatty.java.stringsimilarity.Cosine
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role

// Bot
lateinit var bot: JDA

// CONSTANTS
const val BOT_PREFIX = "o7"
const val PRIMARY_COLOR = 3447003
const val NB_THREAD = 50

// SECRET ROOM RELATED
var SRID = java.lang.Long.parseLong("181478842274283520")

// Non persistent memory
// val smashPassInstances = HashMap<String, SmashPass>()

/////////////////////////////////////////////
////////      FUNCTIONS         /////////////
/////////////////////////////////////////////

fun randomStatus() {
    bot.presence.setPresence(
        listOf(
            Activity.playing("with Chika-chan"), Activity.watching("Chika-chan")
        ).random(), true
    )
}

/**
 * Attempts to find a user in a channel, first look for account name then for nickname
 *
 * @param guild the guild to look in
 * @param searchText the name to look for
 * @return member
 */
fun findMember(guild: Guild, searchText: String): Member? {
    val cosine = Cosine()
    var bestMatch: Pair<Double, Member?> = Pair(0.0, null)
    for (member in guild.members) {
        val nickname = member.effectiveName
        val username = member.user.name
        if (nickname.equals(searchText, ignoreCase = true) || username.equals(
                searchText,
                ignoreCase = true
            ) || member.user.id == searchText
        ) {
            return member
        }
        // Score similarity
        val scoreUserName: Double = cosine.similarity(username.toLowerCase(), searchText.toLowerCase())
        val scoreEffectiveName: Double = cosine.similarity(nickname.toLowerCase(), searchText.toLowerCase())
        bestMatch = when {
            scoreUserName > bestMatch.first -> Pair(scoreUserName, member)
            scoreEffectiveName > bestMatch.first -> Pair(scoreEffectiveName, member)
            else -> bestMatch
        }
    }
    return bestMatch.second
}

///////// ROLE FUNCTION ////////
fun addRole(guild: Guild, member: Member, role: Role) {
    guild.addRoleToMember(member, role).queue()
}

fun removeRole(guild: Guild, member: Member, role: Role) {
    guild.removeRoleFromMember(member, role).queue()
}

// Checks if a yousoro emoji exists
fun yousoroEmojiExists(guild: Guild): Boolean {
    return guild.getEmotesByName("yousoro", true).isNotEmpty()
}

// Returns emoji object from guild
fun getEmote(guild: Guild, name: String, ignoreCase: Boolean): Emote? {
    val emote = guild.getEmotesByName(name, ignoreCase)
    return if (emote.isEmpty()) null else emote[0]
}

// Returns emoji syntax for message
fun getEmojiMessage(guild: Guild, name: String): String {
    val emote = getEmote(guild, name, false) ?: return ""
    return "<:" + name + ":" + emote.id + ">"
}

fun getYousoro(guild: Guild): String {
    val emote = getEmote(guild, "yousoro", false) ?: return "(> ᴗ •)ゞ"
    return "<:yousoro:" + emote.id + ">"
}
