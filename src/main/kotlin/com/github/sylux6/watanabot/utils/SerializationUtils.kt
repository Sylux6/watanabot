package com.github.sylux6.watanabot.utils

import kotlinx.serialization.builtins.list
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * A function to serialize a list of String into a String
 */
fun serializeListOfStrings(list: List<String>): String {
    val json = Json(JsonConfiguration.Stable)
    return json.stringify(String.serializer().list, list)
}

/**
 * A function to deserialize a String into a list of String
 */
fun deserializeListOfStrings(string: String): List<String> {
    val json = Json(JsonConfiguration.Stable)
    return json.parse(String.serializer().list, string)
}
