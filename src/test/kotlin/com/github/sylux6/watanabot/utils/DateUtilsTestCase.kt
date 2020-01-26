package com.github.sylux6.watanabot.utils

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class DateUtilsTestCase : StringSpec() {
    init {
        "it should return 1st Jan" {
            dayFormatter("1 Jan").shouldBe("1st of Jan")
        }
        "it should return 2nd Jan" {
            dayFormatter("2 Jan").shouldBe("2nd of Jan")
        }
        "it should return 3rd Jan" {
            dayFormatter("3 Jan").shouldBe("3rd of Jan")
        }
        "it should return 4th Jan" {
            dayFormatter("4 Jan").shouldBe("4th of Jan")
        }
        "it should return 21st Jan" {
            dayFormatter("21 Jan").shouldBe("21st of Jan")
        }
        "it should return 22nd Jan" {
            dayFormatter("22 Jan").shouldBe("22nd of Jan")
        }
        "it should return 23rd Jan" {
            dayFormatter("23 Jan").shouldBe("23rd of Jan")
        }
        "it should return 31st Jan" {
            dayFormatter("31 Jan").shouldBe("31st of Jan")
        }

    }
}