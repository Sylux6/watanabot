package com.github.sylux6.watanabot.utils

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.longType
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType
import java.io.File

/**
 * Bot properties. Properties are defined in watanabot.properties file. If some of them are defined in environment
 * variables then they override the file properties.
 */
val config = EnvironmentVariables overriding ConfigurationProperties.fromFile(File("watanabot.properties"))

// Bot properties definition

/**
 * Discord bot token
 */
val BOT_TOKEN = Key("bot.token", stringType)

/**
 * Bot prefix command
 */
val BOT_PREFIX = Key("bot.prefix", stringType)

/**
 * Guild server id, used to print logs and other specific stuff. You should not need this
 */
val BOT_PRIVATE_SERVER_ID = Key("bot.private_server_id", longType)
