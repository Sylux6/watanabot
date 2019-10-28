package utils;

import modules.poll.entity.SmashPass;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.utils.IOUtil;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BotUtils {
    // Bot
    static public JDA bot;
    
    // RNG
    static public Random random = new Random();

    // CONSTANTS
    static public String BOT_PREFIX = "o7";
    static public int PREFIX_LENGTH = BOT_PREFIX.length();
    static public int NB_THREAD = 50;
    
    // SECRET ROOM RELATED
    static public long SRID = Long.parseLong("181478842274283520");

    // Non persistent memory
    static public HashMap<String, SmashPass> smashPassInstances = new HashMap<>();
    
    /////////////////////////////////////////////
    ////////      FUNCTIONS         /////////////
    /////////////////////////////////////////////

    /**
     * Attempts to find a user in a channel, first look for account name then for nickname
     *
     * @param guild    the guild to look in
     * @param searchText the name to look for
     * @return IUser | null
     */
    public static Member findMember(Guild guild, String searchText) {
        List<Member> users = guild.getMembers();
        List<Member> potential = new ArrayList<>();
        int smallestDiffIndex = 0, smallestDiff = -1;
        for (Member u : users) {
            String nick = u.getEffectiveName();
            if (nick.equalsIgnoreCase(searchText)) {
                return u;
            }
            if (nick.toLowerCase().contains(searchText)) {
                potential.add(u);
                int d = Math.abs(nick.length() - searchText.length());
                if (d < smallestDiff || smallestDiff == -1) {
                    smallestDiff = d;
                    smallestDiffIndex = potential.size() - 1;
                }
            }
        }
        if (!potential.isEmpty()) {
            return potential.get(smallestDiffIndex);
        }
        return null;
    }

    ///////// ROLE FUNCTION ////////
    static public void addRole(Guild guild, Member member, Role role) {
	    guild.addRoleToMember(member, role).queue();
    }
    
    static public void removeRole(Guild guild, Member member, Role role) {
	guild.removeRoleFromMember(member, role).queue();
    }

    // Checks if a yousoro emoji exists
    static public boolean yousoroEmojiExists(Guild guild) {
	return !guild.getEmotesByName("yousoro", true).isEmpty();
    }
    
    // Returns emoji object from guild
    static public Emote getEmote(Guild guild, String name, boolean ignoreCase) {
	List<Emote> emote = guild.getEmotesByName(name, ignoreCase);
	if (emote.isEmpty())
	    return null;
	return emote.get(0);
    }
    
    // Returns emoji syntax for message
    static public String getEmojiMessage(Guild guild, String name) {
	Emote emote = getEmote(guild, name, false);
	if (emote == null)
	    return "";
	return "<:"+name+":"+emote.getId()+">";
    }
    
    static public String getYousoro(Guild guild) {
	Emote emote = getEmote(guild, "yousoro", false);
	if (emote == null)
	    return "(> ᴗ •)ゞ";
	return "<:yousoro:"+emote.getId()+">";
    }

}
