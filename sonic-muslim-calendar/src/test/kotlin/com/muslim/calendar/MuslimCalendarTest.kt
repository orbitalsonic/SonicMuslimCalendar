package com.muslim.calendar

import java.time.LocalDate
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class MuslimCalendarTest {
    private val supportedLocales = listOf(
        Locale.ENGLISH,
        Locale("ar"),
        Locale("ur", "PK"),
        Locale("id"),
        Locale("hi"),
        Locale("bn"),
        Locale("fa"),
        Locale("tr"),
        Locale("am"),
        Locale.FRENCH,
        Locale("ms"),
        Locale("az"),
        Locale("kk"),
        Locale("ky"),
        Locale("tg"),
        Locale("ru"),
        Locale.GERMAN,
        Locale("sv"),
        Locale("th"),
        Locale("fil"),
        Locale.JAPANESE,
        Locale("es"),
        Locale.KOREAN,
        Locale("sw"),
        Locale("pt")
    )

    @Test
    fun gregorianToHijriConversionIsAccurate() {
        val converted = MuslimCalendar.fromGregorian(LocalDate.of(2015, 3, 14))
        assertEquals(1436, converted.year)
        assertEquals(5, converted.month)
        assertEquals(23, converted.day)
    }

    @Test
    fun hijriToGregorianConversionIsAccurate() {
        val hijri = MuslimCalendar(1436, 5, 23)
        assertEquals(LocalDate.of(2015, 3, 14), hijri.toGregorian())
    }

    @Test
    fun positiveOffsetAppliesAfterConversion() {
        val date = MuslimCalendar.fromGregorian(LocalDate.of(2015, 3, 14), offsetDays = 1)
        assertEquals("24-05-1436", date.format("dd-MM-yyyy", Locale.ENGLISH))
    }

    @Test
    fun negativeOffsetCanRollBackToPreviousMonth() {
        val base = MuslimCalendar(1446, 9, 1, offsetDays = -1)
        assertEquals("30-08-1446", base.format("dd-MM-yyyy", Locale.ENGLISH))
    }

    @Test
    fun offsetRollsOverYearBoundary() {
        val last = MuslimCalendar(1446, 12, MuslimCalendar.lengthOfMonth(1446, 12), offsetDays = 1)
        assertEquals("01-01-1447", last.format("dd-MM-yyyy", Locale.ENGLISH))
    }

    @Test
    fun offsetBoundariesAreEnforced() {
        assertFailsWith<IllegalArgumentException> { MuslimCalendar(1446, 9, 1, offsetDays = 4) }
        assertFailsWith<IllegalArgumentException> { MuslimCalendar(1446, 9, 1, offsetDays = -4) }
    }

    @Test
    fun invalidDateInputIsRejected() {
        assertFailsWith<IllegalArgumentException> { MuslimCalendar(1400, 13, 1) }
        assertFailsWith<IllegalArgumentException> { MuslimCalendar(1400, 2, 31) }
    }

    @Test
    fun formatterAndParserRoundtrip() {
        val original = MuslimCalendar(1446, 9, 10)
        val text = original.format("dd MMM yyyy", Locale.ENGLISH)
        val parsed = MuslimCalendar.parse(text, "dd MMM yyyy", Locale.ENGLISH)
        assertEquals(original.year, parsed.year)
        assertEquals(original.month, parsed.month)
        assertEquals(original.day, parsed.day)
    }

    @Test
    fun localeProviderSupportsArabicAndUrdu() {
        val date = MuslimCalendar(1446, 9, 1)
        assertTrue(date.getMonthName(Locale("ar")).isNotBlank())
        assertTrue(date.getMonthName(Locale("ur", "PK")).isNotBlank())
        assertTrue(date.getDayName(Locale("ar")).isNotBlank())
    }

    @Test
    fun conversionRoundTripMatrixStaysStable() {
        val samples = listOf(
            LocalDate.of(1937, 3, 14),
            LocalDate.of(1989, 2, 25),
            LocalDate.of(1999, 4, 1),
            LocalDate.of(2015, 3, 14),
            LocalDate.of(2024, 7, 7),
            LocalDate.of(2077, 11, 16)
        )

        samples.forEach { date ->
            val hijri = MuslimCalendar.fromGregorian(date)
            assertEquals(date, hijri.toGregorian(), "Roundtrip failed for $date")
        }
    }

    @Test
    fun fromGregorianRejectsInvalidOffset() {
        assertFailsWith<IllegalArgumentException> {
            MuslimCalendar.fromGregorian(LocalDate.of(2025, 1, 1), 4)
        }
        assertFailsWith<IllegalArgumentException> {
            MuslimCalendar.fromGregorian(LocalDate.of(2025, 1, 1), -4)
        }
    }

    @Test
    fun setOffsetValidatesRange() {
        val base = MuslimCalendar(1446, 9, 1)
        assertFailsWith<IllegalArgumentException> { base.setOffset(5) }
        assertFailsWith<IllegalArgumentException> { base.setOffset(-5) }
    }

    @Test
    fun setOffsetReturnsNewInstance() {
        val base = MuslimCalendar(1446, 9, 1)
        val shifted = base.setOffset(1)
        assertEquals(0, base.offsetDays)
        assertEquals(1, shifted.offsetDays)
        assertNotEquals(base, shifted)
    }

    @Test
    fun offsetZeroKeepsDateUntouched() {
        val date = MuslimCalendar(1446, 10, 10, offsetDays = 0)
        assertEquals("10-10-1446", date.format("dd-MM-yyyy"))
    }

    @Test
    fun maxPositiveAndNegativeOffsetsWork() {
        val base = MuslimCalendar(1446, 9, 10)
        assertEquals(13, base.setOffset(3).format("d", Locale.ENGLISH).toInt())
        assertEquals(7, base.setOffset(-3).format("d", Locale.ENGLISH).toInt())
    }

    @Test
    fun leapYearAndYearLengthAreConsistent() {
        val years = listOf(1439, 1440, 1441, 1442, 1443, 1444, 1445, 1446, 1447)
        years.forEach { year ->
            val length = MuslimCalendar.lengthOfYear(year)
            assertTrue(length == 354 || length == 355)
            assertEquals(length == 355, MuslimCalendar.isLeapYear(year))
        }
    }

    @Test
    fun lengthOfMonthMatchesMonthBoundaryDifference() {
        val year = 1446
        for (month in 1..11) {
            val current = MuslimCalendar.lengthOfMonth(year, month)
            val startCurrent = MuslimCalendar(year, month, 1).toGregorian()
            val startNext = MuslimCalendar(year, month + 1, 1).toGregorian()
            assertEquals(current.toLong(), java.time.temporal.ChronoUnit.DAYS.between(startCurrent, startNext))
        }
    }

    @Test
    fun invalidYearRangeRejected() {
        assertFailsWith<IllegalArgumentException> { MuslimCalendar(1200, 1, 1) }
        assertFailsWith<IllegalArgumentException> { MuslimCalendar(1700, 1, 1) }
    }

    @Test
    fun invalidDayRejectedAtMonthEnd() {
        val days = MuslimCalendar.lengthOfMonth(1446, 9)
        assertFailsWith<IllegalArgumentException> { MuslimCalendar(1446, 9, days + 1) }
    }

    @Test
    fun formatSupportsAllTokens() {
        val date = MuslimCalendar(1446, 9, 1)
        val text = date.format("d|dd|M|MM|MMM|MMMM|y|yy|yyyy|E|EEEE", Locale.ENGLISH)
        assertTrue(text.contains("1|01|9|09|"))
        assertTrue(text.contains("|46|1446|"))
    }

    @Test
    fun formatUnknownTokenIsEchoed() {
        val date = MuslimCalendar(1446, 9, 1)
        assertTrue(date.format("Q", Locale.ENGLISH).contains("Q"))
    }

    @Test
    fun parseNumericPatternWorks() {
        val parsed = MuslimCalendar.parse("01-09-1446", "dd-MM-yyyy", Locale.ENGLISH)
        assertEquals(1446, parsed.year)
        assertEquals(9, parsed.month)
        assertEquals(1, parsed.day)
    }

    @Test
    fun parseWithDayNameAndMonthNameWorks() {
        val original = MuslimCalendar(1446, 9, 12)
        val text = original.format("EEEE, dd MMMM yyyy", Locale.ENGLISH)
        val parsed = MuslimCalendar.parse(text, "EEEE, dd MMMM yyyy", Locale.ENGLISH)
        assertEquals(original.copy(offsetDays = 0), parsed)
    }

    @Test
    fun parseTwoDigitYearMapsTo1400Base() {
        val parsed = MuslimCalendar.parse("01-09-46", "dd-MM-yy", Locale.ENGLISH)
        assertEquals(1446, parsed.year)
        assertEquals(9, parsed.month)
        assertEquals(1, parsed.day)
    }

    @Test
    fun parseRejectsBadDelimiter() {
        assertFailsWith<IllegalArgumentException> {
            MuslimCalendar.parse("01/09/1446", "dd-MM-yyyy", Locale.ENGLISH)
        }
    }

    @Test
    fun parseRejectsTrailingInput() {
        assertFailsWith<IllegalArgumentException> {
            MuslimCalendar.parse("01-09-1446x", "dd-MM-yyyy", Locale.ENGLISH)
        }
    }

    @Test
    fun parseRejectsUnknownMonthName() {
        assertFailsWith<IllegalStateException> {
            MuslimCalendar.parse("01 Foo 1446", "dd MMM yyyy", Locale.ENGLISH)
        }
    }

    @Test
    fun parseRejectsUnknownDayName() {
        assertFailsWith<IllegalStateException> {
            MuslimCalendar.parse("Xxx, 01 Muharram 1446", "EEE, dd MMMM yyyy", Locale.ENGLISH)
        }
    }

    @Test
    fun localeFallbackFallsBackToEnglish() {
        val date = MuslimCalendar(1446, 9, 1)
        val unknown = Locale("xx", "YY")
        assertEquals(date.getMonthName(Locale.ENGLISH), date.getMonthName(unknown))
        assertEquals(date.getDayName(Locale.ENGLISH), date.getDayName(unknown))
    }

    @Test
    fun allSupportedLocalesHaveMonthAndDayNames() {
        val date = MuslimCalendar(1446, 9, 1)
        supportedLocales.forEach { locale ->
            assertTrue(date.getMonthName(locale).isNotBlank(), "month name blank for $locale")
            assertTrue(date.getDayName(locale).isNotBlank(), "day name blank for $locale")
            assertTrue(date.format("MMM", locale).isNotBlank(), "short month blank for $locale")
            assertTrue(date.format("E", locale).isNotBlank(), "short day blank for $locale")
        }
    }

    @Test
    fun parseAndFormatAcrossMultipleLocales() {
        val date = MuslimCalendar(1446, 9, 18)
        listOf(Locale.ENGLISH, Locale("ar"), Locale("ur", "PK"), Locale("id"), Locale("tr")).forEach { locale ->
            val text = date.format("dd MMM yyyy", locale)
            val parsed = MuslimCalendar.parse(text, "dd MMM yyyy", locale)
            assertEquals(date.copy(offsetDays = 0), parsed, "roundtrip failed for $locale")
        }
    }

    @Test
    fun monthAndDayNameUseOffsetAdjustedDate() {
        val base = MuslimCalendar(1446, 9, 1)
        val shifted = base.setOffset(-1)
        assertNotEquals(base.getMonthName(Locale.ENGLISH), shifted.getMonthName(Locale.ENGLISH))
    }
}
