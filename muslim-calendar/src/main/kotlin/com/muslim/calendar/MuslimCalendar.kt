package com.muslim.calendar

import java.time.LocalDate
import java.util.Locale

/**
 * Kotlin-first Umm Al-Qura Hijri date model.
 *
 * Month is 1-based (1..12). Offset is applied after conversion and can be in -3..3.
 */
data class MuslimCalendar(
    val year: Int,
    val month: Int,
    val day: Int,
    val offsetDays: Int = 0
) {
    init {
        require(offsetDays in -3..3) { "offsetDays must be between -3 and 3" }
        UmmAlQuraData.validateHijriRange(year, month, day)
    }

    fun setOffset(days: Int): MuslimCalendar = copy(offsetDays = days)

    fun toGregorian(): LocalDate {
        val value = adjusted()
        return HijriConverter.toGregorian(value)
    }

    fun getMonthName(locale: Locale = Locale.ENGLISH): String =
        LocaleProvider.monthName(adjusted().month, locale, short = false)

    fun getDayName(locale: Locale = Locale.ENGLISH): String =
        LocaleProvider.dayName(toGregorian().dayOfWeek, locale, short = false)

    fun format(pattern: String, locale: Locale = Locale.ENGLISH): String =
        Formatter.format(this, pattern, locale)

    internal fun adjusted(): HijriDate {
        if (offsetDays == 0) return HijriDate(year, month, day)
        val shifted = HijriConverter.toGregorian(HijriDate(year, month, day)).plusDays(offsetDays.toLong())
        return HijriConverter.fromGregorian(shifted)
    }

    companion object {
        fun fromGregorian(date: LocalDate, offsetDays: Int = 0): MuslimCalendar {
            require(offsetDays in -3..3) { "offsetDays must be between -3 and 3" }
            val converted = HijriConverter.fromGregorian(date)
            return MuslimCalendar(converted.year, converted.month, converted.day, offsetDays)
        }

        fun parse(input: String, pattern: String, locale: Locale = Locale.ENGLISH): MuslimCalendar =
            Parser.parse(input, pattern, locale)

        fun lengthOfMonth(year: Int, month: Int): Int = HijriConverter.lengthOfMonth(year, month)

        fun lengthOfYear(year: Int): Int = HijriConverter.lengthOfYear(year)

        fun isLeapYear(year: Int): Boolean = HijriConverter.isLeapYear(year)
    }
}
