package com.muslim.calendar

import java.time.DayOfWeek
import java.util.Locale

object Formatter {
    fun format(calendar: MuslimCalendar, pattern: String, locale: Locale = Locale.ENGLISH): String {
        val adjusted = calendar.adjusted()
        val dayOfWeek = calendar.toGregorian().dayOfWeek
        val out = StringBuilder(pattern.length + 16)
        var i = 0

        while (i < pattern.length) {
            val ch = pattern[i]
            if (!ch.isLetter()) {
                out.append(ch)
                i++
                continue
            }
            var run = 1
            while (i + run < pattern.length && pattern[i + run] == ch) run++
            out.append(renderToken(ch, run, adjusted.day, adjusted.month, adjusted.year, dayOfWeek, locale))
            i += run
        }
        return out.toString()
    }

    private fun renderToken(
        token: Char,
        size: Int,
        day: Int,
        month: Int,
        year: Int,
        dayOfWeek: DayOfWeek,
        locale: Locale
    ): String = when (token) {
        'd' -> if (size >= 2) day.toString().padStart(2, '0') else day.toString()
        'M' -> when {
            size >= 4 -> LocaleProvider.monthName(month, locale, false)
            size == 3 -> LocaleProvider.monthName(month, locale, true)
            size == 2 -> month.toString().padStart(2, '0')
            else -> month.toString()
        }
        'y' -> when {
            size >= 4 -> year.toString().padStart(4, '0')
            size == 2 -> (year % 100).toString().padStart(2, '0')
            else -> year.toString()
        }
        'E' -> if (size >= 4) LocaleProvider.dayName(dayOfWeek, locale, false) else LocaleProvider.dayName(dayOfWeek, locale, true)
        else -> token.toString().repeat(size)
    }
}
