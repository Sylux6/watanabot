package com.github.sylux6.watanabot.utils

import info.debatty.java.stringsimilarity.Cosine
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role

// Bot
lateinit var jdaInstance: JDA

// CONSTANTS
val BOT_PREFIX = config.getOrElse(CONFIG_PREFIX, "o7")
val PRIVATE_SERVER_ID = config.getOrNull(CONFIG_PRIVATE_SERVER_ID)
const val BOT_PRIMARY_COLOR = 3447003

val isPrivateServer: (Long?) -> (Boolean) = { id: Long? -> id == PRIVATE_SERVER_ID }

// Non persistent memory
// val smashPassInstances = HashMap<String, SmashPass>()

// ///////////////////////////////////////////
// //////      FUNCTIONS         /////////////
// ///////////////////////////////////////////

/**
 * Attempts to find a user in a channel, first look for account name then for nickname
 *
 * @param guild the guild to look in
 * @param searchText the name to look for
 * @return member
 */
fun findMemberOrNull(guild: Guild, searchText: String): Member? {
    if (DISCORD_MENTION.matches(searchText)) {
        return guild.getMemberById(searchText.drop(2).dropLast(1))
    }
    if (DISCORD_TAG.matches(searchText)) {
        return guild.getMemberByTag(searchText)
    }
    if (guild.members.map { member -> member.id }.contains(searchText)) {
        return guild.getMemberById(searchText)
    }
    val cosine = Cosine()
    var bestMatch: Pair<Double, Member?> = 0.0 to null
    for (member in guild.members) {
        val nickname = member.effectiveName.toLowerCase()
        val username = member.user.name.toLowerCase()
        if (nickname == searchText || username == searchText || member.user.id == searchText) {
            return member
        }
        // Score similarity
        val scoreUserName: Double = cosine.similarity(username, searchText.toLowerCase())
        val scoreEffectiveName: Double = cosine.similarity(nickname, searchText.toLowerCase())
        bestMatch = when {
            scoreUserName > bestMatch.first -> scoreUserName to member
            scoreEffectiveName > bestMatch.first -> scoreEffectiveName to member
            else -> bestMatch
        }
    }
    return bestMatch.second
}

// /////// ROLE FUNCTION ////////
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
fun getEmojiMessage(guild: Guild, name: String): String? {
    val emote = getEmote(guild, name, false) ?: return null
    return "<:" + name + ":" + emote.id + ">"
}

fun getYousoro(guild: Guild): String {
    val emote = getEmote(guild, "yousoro", false) ?: return "(> ᴗ •)ゞ"
    return "<:yousoro:" + emote.id + ">"
}
