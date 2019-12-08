package utils

import com.github.azurapi.azurapikotlin.api.Atago
import commands.poll.entity.SmashPass
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.*
import java.util.*
import kotlin.math.abs

object BotUtils {
    // Bot
    lateinit var bot: JDA

    // RNG
    var random = Random()

    // CONSTANTS
    const val BOT_PREFIX = "o7"
    const val PRIMARY_COLOR = 3447003
    const val NB_THREAD = 50

    // Unique instance of Azur Lane API so we avoid building database everytime
    val azurLaneApi = Atago()

    // SECRET ROOM RELATED
    var SRID = java.lang.Long.parseLong("181478842274283520")

    // Non persistent memory
    var smashPassInstances = HashMap<String, SmashPass>()

    /////////////////////////////////////////////
    ////////      FUNCTIONS         /////////////
    /////////////////////////////////////////////

    fun randomStatus() {
        bot.presence.setPresence(listOf(
                Activity.playing("with Chika-chan"), Activity.watching("Chika-chan")).random(), true)
    }

    /**
     * Attempts to find a user in a channel, first look for account name then for nickname
     *
     * @param guild the guild to look in
     * @param searchText the name to look for
     * @return IUser | null
     */
    fun findMember(guild: Guild, searchText: String): Member? {
        val users = guild.members
        val potential = ArrayList<Member>()
        var smallestDiffIndex = 0
        var smallestDiff = -1
        for (u in users) {
            val nick = u.effectiveName
            val username = u.user.name
            if (nick.equals(searchText, ignoreCase = true) || username.equals(searchText, ignoreCase = true)) {
                return u
            }
            if (nick.toLowerCase().contains(searchText) || username.toLowerCase().contains(searchText)) {
                potential.add(u)
                val d = abs(nick.length - searchText.length)
                if (d < smallestDiff || smallestDiff == -1) {
                    smallestDiff = d
                    smallestDiffIndex = potential.size - 1
                }
            }
        }
        return if (potential.isNotEmpty()) {
            potential[smallestDiffIndex]
        } else null
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

}
