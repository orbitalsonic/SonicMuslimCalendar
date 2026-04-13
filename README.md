# Muslim-Calendar

Kotlin implementation of the **Umm Al-Qura (Hijri) calendar** for JVM and Android-compatible projects.

![Kotlin](https://img.shields.io/badge/Kotlin-2.x-7F52FF?logo=kotlin&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg)
![Platform](https://img.shields.io/badge/Platform-JVM%20%7C%20Android-blue)

## ✨ Overview

`Muslim-Calendar` is a clean Kotlin-first library for working with Hijri dates using the Umm Al-Qura calendar model.

It provides:

- Accurate Umm Al-Qura date calculations
- Gregorian ↔ Hijri conversion
- Localization support
- Lightweight formatting and parsing APIs
- Manual date offset support (`-3..+3`) for regional calendar adjustments

## 🚀 Features

- 📅 **Umm Al-Qura calculations** backed by precomputed table data
- 🔁 **Bidirectional conversion** between `LocalDate` and Hijri dates
- 🌍 **Localized month/day names** (English, Arabic, Urdu)
- 🧩 **Pattern-based formatter/parser** (no heavy `java.text` dependency)
- ⏱️ **Manual offset** support to handle country-specific differences
- 🧱 **Library module design** suitable for reuse across projects

## 📦 Installation (Gradle Kotlin DSL)

Add the module dependency:

```kotlin
dependencies {
    implementation(project(":muslim-calendar"))
}
```

If you publish it to a Maven repository later, replace with the published artifact coordinates.

## 🛠️ Usage

### 1) Get current Hijri date

```kotlin
import com.muslim.calendar.MuslimCalendar
import java.time.LocalDate

val todayHijri = MuslimCalendar.fromGregorian(LocalDate.now())
println(todayHijri) // MuslimCalendar(year=..., month=..., day=..., offsetDays=0)
```

### 2) Create a custom Hijri date

```kotlin
val hijri = MuslimCalendar(year = 1446, month = 9, day = 1)
```

### 3) Gregorian → Hijri

```kotlin
val hijri = MuslimCalendar.fromGregorian(LocalDate.of(2025, 3, 1))
```

### 4) Hijri → Gregorian

```kotlin
val gregorian = MuslimCalendar(1446, 9, 1).toGregorian()
println(gregorian) // e.g. 2025-03-01
```

### 5) Offset usage (+1 / -1)

```kotlin
val saudi = MuslimCalendar.fromGregorian(LocalDate.now(), offsetDays = 0)
val pakistan = MuslimCalendar.fromGregorian(LocalDate.now(), offsetDays = 1)
val india = MuslimCalendar.fromGregorian(LocalDate.now(), offsetDays = -1)
```

Or update an existing Hijri date:

```kotlin
val base = MuslimCalendar(1446, 9, 1)
val adjusted = base.setOffset(1)
```

### 6) Localization (Arabic, Urdu)

```kotlin
import java.util.Locale

val date = MuslimCalendar(1446, 9, 1)

println(date.getMonthName(Locale("ar")))       // Arabic month name
println(date.getDayName(Locale("ur", "PK")))   // Urdu day name
```

### 7) Formatting

Supported tokens:

- `d`, `dd`
- `M`, `MM`, `MMM`, `MMMM`
- `y`, `yy`, `yyyy`
- `E`, `EEEE`

```kotlin
val date = MuslimCalendar(1446, 9, 1)
val text = date.format("EEEE, dd MMMM yyyy", Locale.ENGLISH)
println(text)
```

### 8) Parsing

```kotlin
val parsed = MuslimCalendar.parse(
    input = "01 Ramadan 1446",
    pattern = "dd MMMM yyyy",
    locale = Locale.ENGLISH
)
```

## ⚙️ How It Works

### Calendar Model

Umm Al-Qura is a **tabular/precomputed civil calendar**.  
It is **not** based on real-time moon sighting calculations.

### Internal Algorithm

The library converts dates using **Julian Day Number (JDN)** as the intermediate representation:

`Gregorian -> JDN -> Umm Al-Qura lookup table -> Hijri`

and reverse:

`Hijri -> Umm Al-Qura lookup table -> JDN -> Gregorian`

### Date Range

- Supported range: **1300H – 1600H**

### Offset Behavior

- Manual offset range: **-3..+3**
- Applied **after conversion**
- Useful for country-level differences (e.g., Saudi Arabia vs Pakistan/India)

## 🌍 Locale Support

Built-in primary locale support includes:

- English
- Arabic
- Urdu

The localization layer is map-based and easy to extend.  
To add more languages, update `LocaleProvider` with:

- full month names
- short month names
- full day names
- short day names

## ⚠️ Limitations

- Valid only for the supported Umm Al-Qura date range
- Offset is a manual adjustment, not an astronomical calculation
- Not a real-time moon-sighting engine

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

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Add/adjust tests for your changes
4. Run tests locally
5. Open a pull request with a clear description

## 📄 License

This project is licensed under the **MIT License**.

## 🙏 Credits

This project is inspired by:

- [msarhan/ummalqura-calendar](https://github.com/msarhan/ummalqura-calendar)

