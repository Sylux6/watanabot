package com.github.sylux6.watanabot.utils

import com.natpryce.konfig.Configuration
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.Misconfiguration
import com.natpryce.konfig.intType
import com.natpryce.konfig.longType
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType
import java.io.File
import kotlin.system.exitProcess

/**
 * Bot properties. Properties are defined in watanabot.properties file. If some of them are defined in environment
 * variables then they override the file properties.
 */
val config: Configuration by lazy {
    try {
        EnvironmentVariables overriding ConfigurationProperties.fromFile(File("watanabot.properties"))
    } catch (e: Misconfiguration) {
        println("watanabot.properties file not found")
        exitProcess(1)
    }
}

fun getToken(): String {
    try {
        return config[CONFIG_TOKEN]
    } catch (e: Misconfiguration) {
        println("Please define a bot.token property in properties file")
        exitProcess(1)
    }
}

// Bot properties definition

/**
 * Sentry DNS
 */
val SENTRY_DNS = Key("log.sentry_dns", stringType)

/**
 * Discord bot token
 */
val CONFIG_TOKEN = Key("bot.token", stringType)

/**
 * Bot prefix command
 */
val CONFIG_PREFIX = Key("bot.prefix", stringType)

/**
 * Guild server id, used to print logs and other specific stuff. You should not need this
 */
val CONFIG_PRIVATE_SERVER_ID = Key("bot.private_server_id", longType)

/**
 * Database host
 */
val CONFIG_DB_HOST = Key("db.host", stringType)

/**
 * Database port
 */
val CONFIG_DB_PORT = Key("db.port", intType)

/**
 * Database name
 */
val CONFIG_DB_NAME = Key("db.name", stringType)

/**
 * Database user
 */
val CONFIG_DB_USER = Key("db.user", stringType)

/**
 * Database password
 */
val CONFIG_DB_PASSWORD = Key("db.password", stringType)
