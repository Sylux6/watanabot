package com.github.sylux6.watanabot.utils

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.list
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

/**
 * A function to serialize a list of String into a String
 */
fun serializeListOfStrings(list: List<String>): String {
    val json = Json { allowStructuredMapKeys = true }
    return json.encodeToString(ListSerializer(String.serializer()), list)
}

/**
 * A function to deserialize a String into a list of String
 */
fun deserializeListOfStrings(string: String): List<String> {
    val json = Json { allowStructuredMapKeys = true }
    return json.decodeFromString(ListSerializer(String.serializer()), string)
}
