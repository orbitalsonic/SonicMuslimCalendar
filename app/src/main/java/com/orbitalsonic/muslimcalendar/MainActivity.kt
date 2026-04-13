package com.orbitalsonic.muslimcalendar

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muslim.calendar.MuslimCalendar
import java.time.LocalDate
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val tag = "MuslimCalendarDemo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val root = buildDemoUi()
        setContentView(root)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun buildDemoUi(): ScrollView {
        val scrollView = ScrollView(this)
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val padding = dp(16)
            setPadding(padding, padding, padding, padding)
        }
        scrollView.addView(container)

        val today = LocalDate.now()
        val currentHijri = MuslimCalendar.fromGregorian(today)

        addSection(
            container,
            "1. Current Hijri Date",
            listOf(
                "Today (Gregorian): $today",
                "Current Hijri Date: ${currentHijri.year} ${currentHijri.getMonthName(Locale.ENGLISH)} ${currentHijri.day}"
            )
        )

        val convertedFromGregorian = MuslimCalendar.fromGregorian(today)
        addSection(
            container,
            "2. Gregorian → Hijri",
            listOf("Gregorian $today -> Hijri ${convertedFromGregorian.format("dd MMMM yyyy", Locale.ENGLISH)}")
        )

        val customHijri = MuslimCalendar(1446, 9, 10)
        val customGregorian = customHijri.toGregorian()
        addSection(
            container,
            "3. Hijri → Gregorian",
            listOf(
                "Custom Hijri: ${customHijri.format("dd MMMM yyyy", Locale.ENGLISH)}",
                "Converted Gregorian: $customGregorian"
            )
        )

        val offsetZero = customHijri.setOffset(0)
        val offsetPlus = customHijri.setOffset(1)
        val offsetMinus = customHijri.setOffset(-1)
        addSection(
            container,
            "4. Offset Feature",
            listOf(
                "Offset 0 : ${offsetZero.format("dd MMMM yyyy", Locale.ENGLISH)}",
                "Offset +1: ${offsetPlus.format("dd MMMM yyyy", Locale.ENGLISH)}",
                "Offset -1: ${offsetMinus.format("dd MMMM yyyy", Locale.ENGLISH)}"
            )
        )

        addSection(
            container,
            "5. Localization",
            listOf(
                "English: ${customHijri.getDayName(Locale.ENGLISH)}, ${customHijri.getMonthName(Locale.ENGLISH)}",
                "Arabic : ${customHijri.getDayName(Locale("ar"))}, ${customHijri.getMonthName(Locale("ar"))}",
                "Urdu   : ${customHijri.getDayName(Locale("ur", "PK"))}, ${customHijri.getMonthName(Locale("ur", "PK"))}"
            )
        )

        addSection(
            container,
            "6. Formatting",
            listOf(
                "Pattern [dd MMMM yyyy]: ${customHijri.format("dd MMMM yyyy", Locale.ENGLISH)}",
                "Pattern [EEEE, d MMMM yyyy]: ${customHijri.format("EEEE, d MMMM yyyy", Locale.ENGLISH)}"
            )
        )

        val parseInput = "10 Ramadan 1446"
        val parsedDate = MuslimCalendar.parse(parseInput, "dd MMMM yyyy", Locale.ENGLISH)
        addSection(
            container,
            "7. Parsing",
            listOf(
                "Input: $parseInput",
                "Parsed: ${parsedDate.format("dd MMMM yyyy", Locale.ENGLISH)}"
            )
        )

        addSection(
            container,
            "8. Month & Year Info",
            listOf(
                "Length of month (${customHijri.month}/${customHijri.year}): ${MuslimCalendar.lengthOfMonth(customHijri.year, customHijri.month)} days",
                "Length of year (${customHijri.year}): ${MuslimCalendar.lengthOfYear(customHijri.year)} days",
                "Is leap year (${customHijri.year}): ${MuslimCalendar.isLeapYear(customHijri.year)}"
            )
        )

        return scrollView
    }

    private fun addSection(container: LinearLayout, title: String, lines: List<String>) {
        val titleView = TextView(this).apply {
            text = title
            textSize = 18f
            setPadding(0, dp(8), 0, dp(4))
        }
        container.addView(titleView)

        lines.forEach { line ->
            Log.d(tag, "$title -> $line")
            container.addView(
                TextView(this).apply {
                    text = line
                    textSize = 15f
                    setPadding(0, dp(2), 0, dp(2))
                }
            )
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}