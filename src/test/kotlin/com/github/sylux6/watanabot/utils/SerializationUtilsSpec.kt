package com.github.sylux6.watanabot.utils

import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object SerializationUtilsSpec : Spek({
    describe("Serializer") {
        it("should serialize a list of strings") {
            val list = listOf("Watanabe You", "Matsuura Kanan")
            val expected = """["Watanabe You","Matsuura Kanan"]"""
            expect(serializeListOfStrings(list)).toBe(expected)
        }
        it("should deserialize a list of strings") {
            val text = """["Watanabe You","Matsuura Kanan"]"""
            val expected = listOf("Watanabe You", "Matsuura Kanan")
            expect(deserializeListOfStrings(text)).toBe(expected)
        }
    }
})
