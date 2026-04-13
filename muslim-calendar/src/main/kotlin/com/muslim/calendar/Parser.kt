package com.muslim.calendar

import java.time.DayOfWeek
import java.util.Locale

object Parser {
    fun parse(input: String, pattern: String, locale: Locale = Locale.ENGLISH): MuslimCalendar {
        var year: Int? = null
        var month: Int? = null
        var day: Int? = null
        var pi = 0
        var ii = 0

        while (pi < pattern.length) {
            val p = pattern[pi]
            if (!p.isLetter()) {
                require(ii < input.length && input[ii] == p) { "Expected '$p' at position $ii" }
                pi++
                ii++
                continue
            }

            var run = 1
            while (pi + run < pattern.length && pattern[pi + run] == p) run++

            when (p) {
                'd' -> {
                    val len = if (run >= 2) 2 else consumeNumberLength(input, ii, 1, 2)
                    day = input.substring(ii, ii + len).toInt()
                    ii += len
                }
                'M' -> {
                    if (run >= 3) {
                        val parsed = parseMonthName(input, ii, locale, run == 3)
                        month = parsed.first
                        ii += parsed.second
                    } else {
                        val len = if (run == 2) 2 else consumeNumberLength(input, ii, 1, 2)
                        month = input.substring(ii, ii + len).toInt()
                        ii += len
                    }
                }
                'y' -> {
                    val len = if (run >= 4) 4 else if (run == 2) 2 else consumeNumberLength(input, ii, 1, 4)
                    val value = input.substring(ii, ii + len).toInt()
                    year = if (run == 2) 1400 + value else value
                    ii += len
                }
                'E' -> {
                    ii += parseDayName(input, ii, locale, run < 4)
                }
                else -> error("Unsupported token: $p")
            }
            pi += run
        }

        require(ii == input.length) { "Unexpected trailing input at position $ii" }
        return MuslimCalendar(
            year = year ?: error("Missing year in input"),
            month = month ?: error("Missing month in input"),
            day = day ?: error("Missing day in input")
        )
    }

    private fun consumeNumberLength(input: String, index: Int, min: Int, max: Int): Int {
        var len = 0
        while (index + len < input.length && input[index + len].isDigit() && len < max) len++
        require(len >= min) { "Expected number at position $index" }
        return len
    }

    private fun parseMonthName(input: String, index: Int, locale: Locale, short: Boolean): Pair<Int, Int> {
        for (month in 1..12) {
            val name = LocaleProvider.monthName(month, locale, short)
            if (input.regionMatches(index, name, 0, name.length, ignoreCase = true)) {
                return month to name.length
            }
        }
        error("Unable to parse month name at position $index")
    }

    private fun parseDayName(input: String, index: Int, locale: Locale, short: Boolean): Int {
        for (day in DayOfWeek.entries) {
            val name = LocaleProvider.dayName(day, locale, short)
            if (input.regionMatches(index, name, 0, name.length, ignoreCase = true)) {
                return name.length
            }
        }
        error("Unable to parse day name at position $index")
    }
}
