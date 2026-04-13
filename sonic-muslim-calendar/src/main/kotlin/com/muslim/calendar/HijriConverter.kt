package com.muslim.calendar

import java.time.LocalDate

internal data class HijriDate(val year: Int, val month: Int, val day: Int)

object HijriConverter {
    internal fun fromGregorian(date: LocalDate): HijriDate {
        val julianDay = gregorianToJulianDay(date.year, date.monthValue, date.dayOfMonth)
        return UmmAlQuraData.julianDayToHijri(julianDay)
    }

    internal fun toGregorian(hijri: HijriDate): LocalDate {
        UmmAlQuraData.validateHijriRange(hijri.year, hijri.month, hijri.day)
        val julianDay = UmmAlQuraData.hijriToJulianDay(hijri.year, hijri.month, hijri.day)
        val (year, month, day) = julianDayToGregorian(julianDay)
        return LocalDate.of(year, month, day)
    }

    fun lengthOfMonth(year: Int, month: Int): Int {
        UmmAlQuraData.validateHijriRange(year, month, 1)
        return UmmAlQuraData.getDaysInMonth(year, month)
    }

    fun lengthOfYear(year: Int): Int = (1..12).sumOf { month -> lengthOfMonth(year, month) }

    fun isLeapYear(year: Int): Boolean = lengthOfYear(year) == 355

    private fun div(a: Int, b: Int): Int = a / b
    private fun mod(a: Int, b: Int): Int = a - ((a / b) * b)

    private fun gregorianToJulianDay(year: Int, month: Int, day: Int): Int {
        var d = div((year + div(month - 8, 6) + 100100) * 1461, 4) +
            div((153 * mod(month + 9, 12)) + 2, 5) +
            day - 34_840_408
        d = d - div((div(year + 100100 + div(month - 8, 6), 100) * 3), 4) + 752
        return d
    }

    private fun julianDayToGregorian(julianDay: Int): Triple<Int, Int, Int> {
        var j = 4 * julianDay + 139_361_631
        j += div((div((4 * julianDay) + 183_187_720, 146_097) * 3), 4) * 4 - 3_908
        val i = div(mod(j, 1461), 4) * 5 + 308
        val day = div(mod(i, 153), 5) + 1
        val month = mod(div(i, 153), 12) + 1
        val year = div(j, 1461) - 100_100 + div(8 - month, 6)
        return Triple(year, month, day)
    }
}
