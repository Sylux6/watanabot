package com.github.sylux6.watanabot.utils

import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object DateUtilsSpec : Spek({
    describe("Date formatter") {
        it("should return 1st of Jan") {
            expect(dayFormatter("1 Jan")).toBe("1st of Jan")
        }
        it("should return 2nd of Jan") {
            expect(dayFormatter("2 Jan")).toBe("2nd of Jan")
        }
        it("should return 3rd of Jan") {
            expect(dayFormatter("3 Jan")).toBe("3rd of Jan")
        }
        it("should return 4th of Jan") {
            expect(dayFormatter("4 Jan")).toBe("4th of Jan")
        }
        it("should return 21st of Jan") {
            expect(dayFormatter("21 Jan")).toBe("21st of Jan")
        }
        it("should return 22nd of Jan") {
            expect(dayFormatter("22 Jan")).toBe("22nd of Jan")
        }
        it("should return 23rd of Jan") {
            expect(dayFormatter("23 Jan")).toBe("23rd of Jan")
        }
        it("should return 31st of Jan") {
            expect(dayFormatter("31 Jan")).toBe("31st of Jan")
        }
    }
})
