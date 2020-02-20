package com.github.sylux6.watanabot.utils

import mu.KLogger
import org.slf4j.Marker
import org.slf4j.MarkerFactory

// Marker info to send to sentry
val infoMarker: Marker = MarkerFactory.getMarker("SENTRY_INFO")

/**
 * Use this instead of logger.info() to send INFO level log to sentry
 *
 * @param message message
 */
fun <T : KLogger> T.log(message: String) {
    info(infoMarker, message)
}
