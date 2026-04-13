# Muslim-Calendar

[![Kotlin](https://img.shields.io/badge/Kotlin-2.x-7F52FF?logo=kotlin&logoColor=white)](#)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](#-license)
[![Platform](https://img.shields.io/badge/Platform-JVM%20%7C%20Android-blue)](#)

Production-ready Hijri calendar library for Umm Al-Qura date handling, conversion, localization, formatting, parsing, and manual day-offset control.

---

## ✨ Overview

`Muslim-Calendar` offers a practical API for:

- Hijri (Umm Al-Qura) date calculations
- Gregorian ↔ Hijri conversion
- Locale-aware month/day names
- Lightweight formatting and parsing
- Manual date offset support (`-3..+3`)

It is designed as a reusable module for Kotlin/JVM and Android projects.

---

## 🚀 Features

- Accurate Umm Al-Qura conversion pipeline
- Kotlin-first, simple API surface
- 25+ locale mappings (including English, Arabic, Urdu)
- Custom formatter/parser without heavy date-format libraries
- Regional adjustment support via `offsetDays`
- Test-covered core functionality

---

## 📦 Installation (Gradle Kotlin DSL)

If you are using it as a local module:

```kotlin
dependencies {
    implementation(project(":muslim-calendar"))
}
```

---

## 🛠️ Usage

### 1) Get current Hijri date

```kotlin
import com.muslim.calendar.MuslimCalendar
import java.time.LocalDate

val todayHijri = MuslimCalendar.fromGregorian(LocalDate.now())
println(todayHijri)
// Output: MuslimCalendar(year=1447, month=9, day=10, offsetDays=0)
```

### 2) Create custom Hijri date

```kotlin
import com.muslim.calendar.MuslimCalendar

val hijri = MuslimCalendar(year = 1446, month = 9, day = 1)
println(hijri)
// Output: MuslimCalendar(year=1446, month=9, day=1, offsetDays=0)
```

### 3) Gregorian -> Hijri

```kotlin
import com.muslim.calendar.MuslimCalendar
import java.time.LocalDate

val hijri = MuslimCalendar.fromGregorian(LocalDate.of(2025, 3, 1))
println(hijri)
// Output: MuslimCalendar(year=1446, month=9, day=1, offsetDays=0)
```

### 4) Hijri -> Gregorian

```kotlin
import com.muslim.calendar.MuslimCalendar

val gregorian = MuslimCalendar(1446, 9, 1).toGregorian()
println(gregorian)
// Output: 2025-03-01
```

### 5) Offset usage (+1 / -1)

```kotlin
import com.muslim.calendar.MuslimCalendar
import java.time.LocalDate
import java.util.Locale

val saudi = MuslimCalendar.fromGregorian(LocalDate.now(), offsetDays = 0)
val pakistan = MuslimCalendar.fromGregorian(LocalDate.now(), offsetDays = 1)
val india = MuslimCalendar.fromGregorian(LocalDate.now(), offsetDays = -1)

println(saudi.format("yyyy-MM-dd", Locale.ENGLISH))
// Output: 1447-09-10

println(pakistan.format("yyyy-MM-dd", Locale.ENGLISH))
// Output: 1447-09-11

println(india.format("yyyy-MM-dd", Locale.ENGLISH))
// Output: 1447-09-09

val base = MuslimCalendar(1446, 9, 1)
val adjusted = base.setOffset(1)

println(adjusted.format("yyyy-MM-dd", Locale.ENGLISH))
// Output: 1446-09-02
```

### 6) Localization (Arabic, Urdu)

```kotlin
import com.muslim.calendar.MuslimCalendar
import java.util.Locale

val date = MuslimCalendar(1446, 9, 1)

println(date.getMonthName(Locale("ar")))
// Output: رمضان

println(date.getDayName(Locale("ur", "PK")))
// Output: پیر
```

### 7) Formatting

Supported tokens:

- `d`, `dd`
- `M`, `MM`, `MMM`, `MMMM`
- `y`, `yy`, `yyyy`
- `E`, `EEEE`

```kotlin
import com.muslim.calendar.MuslimCalendar
import java.util.Locale

val date = MuslimCalendar(1446, 9, 1)
val text = date.format("EEEE, dd MMMM yyyy", Locale.ENGLISH)

println(text)
// Output: Saturday, 01 Ramadan 1446
```

### 8) Parsing

```kotlin
import com.muslim.calendar.MuslimCalendar
import java.util.Locale

val parsed = MuslimCalendar.parse(
    input = "01 Ramadan 1446",
    pattern = "dd MMMM yyyy",
    locale = Locale.ENGLISH
)

println(parsed)
// Output: MuslimCalendar(year=1446, month=9, day=1, offsetDays=0)
```

---

## 🕌 Umm Al-Qura Calendar

Umm Al-Qura is a standardized Islamic civil calendar used in Saudi Arabia.  
It is based on official precomputed calculations and not purely on local real-time moon sighting.

- Official reference: [https://www.ummulqura.org.sa/](https://www.ummulqura.org.sa/)
- Commonly used as a national reference calendar in Saudi Arabia
- Supported range in this library: **1300H – 1600H**

Conversion model:

`Gregorian -> JDN -> Umm Al-Qura table -> Hijri`

`Hijri -> Umm Al-Qura table -> JDN -> Gregorian`

---

## 🌍 Locale Support

- Supports **25+ languages**
- Includes **Arabic, English, and Urdu**
- Locale mappings are easy to extend through `LocaleProvider`

To add a new locale, provide:

- full month names
- short month names
- full day names
- short day names

---

## ⚠️ Limitations

- Valid only inside the supported Umm Al-Qura data range
- `offsetDays` is a manual post-conversion adjustment
- Not an astronomical moon-sighting engine

---

## 📁 Project Structure

```text
muslim-calendar/
├── src/main/kotlin/com/muslim/calendar/
│   ├── MuslimCalendar.kt
│   ├── UmmAlQuraData.kt
│   ├── HijriConverter.kt
│   ├── Formatter.kt
│   ├── Parser.kt
│   └── LocaleProvider.kt
└── src/test/kotlin/com/muslim/calendar/
    └── MuslimCalendarTest.kt
```

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Add or update tests
4. Run checks locally
5. Open a pull request with a clear summary

---

## 🙏 Credits

This library is inspired by:

- [msarhan/ummalqura-calendar](https://github.com/msarhan/ummalqura-calendar)

---

## 📄 License

The MIT License (MIT)

Copyright (c) 2015 Mouaffak A. Sarhan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
