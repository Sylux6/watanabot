package com.github.sylux6.watanabot.internal.types

enum class CommandLevelAccess(val info: String) {
    /**
     * Everyone can use
     */
    EVERYONE(""),
    /**
     * Restricted to users in a voice channel
     */
    IN_VOICE("You must be in a voice channel to run this command"),
    /**
     * Restricted to users in the same voice channel as the bot
     */
    IN_VOICE_WITH_BOT("You must be in the same voice channel as the bot to run this command"),
    /**
     * Only usable when the bot is in a voice channel
     */
    BOT_IN_VOICE("Bot must be in a voice channel to run this command"),
    /**
     * Restricted to mods who can manage channels
     */
    MOD("You must be able to manage channels in this server to run this command"),
    /**
     * Restricted to the owner of the guild only
     */
    ADMIN("You must be the owner of this server to run this command"),
    /**
     * Restricted to the owner of the bot only
     */
    OWNER("You must be the owner of this bot to run this command"),
    /**
     * Restricted to BotUtils.SRID only
     */
    PRIVATE("You cannot run this command in this server")
}
