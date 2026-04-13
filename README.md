[![](https://jitpack.io/v/orbitalsonic/SonicMuslimCalendar.svg)](https://jitpack.io/#orbitalsonic/SonicMuslimCalendar)


# Sonic-Muslim-Calendar

**Sonic-Muslim-Calendar** is a lightweight library for working with Hijri dates using the Umm Al-Qura calendar system.

The Umm Al-Qura calendar is a standardized Islamic civil calendar officially used in Saudi Arabia, based on precomputed astronomical data rather than real-time moon sighting.

- 🌐 Official reference: https://www.ummulqura.org.sa/
- 📍 National reference calendar in Saudi Arabia
- 📆 Supported range: **1300H – 1600H**

## ✨ Overview

`Muslim-Calendar` offers a practical API for:

- Hijri (Umm Al-Qura) date calculations
- Gregorian ↔ Hijri conversion
- Locale-aware month/day names
- Lightweight formatting and parsing
- Manual date offset support (`-3..+3`)

It is designed as a reusable module for Kotlin/JVM and Android projects.


## 🚀 Features

- Accurate Umm Al-Qura conversion pipeline
- Kotlin-first, simple API surface
- 25+ locale mappings (including English, Arabic, Urdu)
- Custom formatter/parser without heavy date-format libraries
- Regional adjustment support via `offsetDays`
- Test-covered core functionality

---

## Setup

### Step 1: Add Maven Repository
Add the following to your project-level build script (`build.gradle` or `settings.gradle`) for **Groovy** or **Kotlin DSL**:

#### Groovy DSL
```groovy
repositories {
   google()
   mavenCentral()
   maven { url "https://jitpack.io" }
}
```

#### Kotlin DSL
```kotlin
repositories {
    google()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}
```

### Step 2: Add Dependency
Include the OPT library in your app-level build script (`build.gradle` or `build.gradle.kts`). Replace `x.x.x` with the latest version: [![](https://jitpack.io/v/orbitalsonic/SonicMuslimCalendar.svg)](https://jitpack.io/#orbitalsonic/SonicMuslimCalendar)


#### Groovy DSL
```groovy
implementation 'com.github.orbitalsonic:SonicMuslimCalendar:x.x.x'

```

#### Kotlin DSL
```kotlin
implementation("com.github.orbitalsonic:SonicMuslimCalendar:x.x.x")
```

### Step 3: Sync Gradle
Sync your Gradle project to fetch the dependency.

---

## 🛠️ Usage

### 1) Get current Hijri date

```kotlin
// val today = LocalDate.now()
val today = LocalDate.of(2026, 4, 13)
val currentHijri = MuslimCalendar.fromGregorian(today)

println("Today (Gregorian): $today")
// Output: Today (Gregorian): 2026-04-13

println("Current Hijri Date: ${currentHijri.year} ${currentHijri.getMonthName(Locale.ENGLISH)} ${currentHijri.day}")
// Output: Current Hijri Date: 1447 Shawwal 25
```

### 2) Create custom Hijri date

```kotlin
val hijri = MuslimCalendar(year = 1446, month = 9, day = 10)
println(hijri)
// Output: MuslimCalendar(year=1446, month=9, day=10, offsetDays=0)
```

### 3) Gregorian -> Hijri

```kotlin
// val today = LocalDate.now()
val today = LocalDate.of(2026, 4, 13)
val convertedFromGregorian = MuslimCalendar.fromGregorian(today)

println("Gregorian $today -> Hijri ${convertedFromGregorian.format("dd MMMM yyyy", Locale.ENGLISH)}")
// Output: Gregorian 2026-04-13 -> Hijri 25 Shawwal 1447
```

### 4) Hijri -> Gregorian

```kotlin
val customHijri = MuslimCalendar(1446, 9, 10)
val customGregorian = customHijri.toGregorian()

println("Custom Hijri: ${customHijri.format("dd MMMM yyyy", Locale.ENGLISH)}")
// Output: Custom Hijri: 10 Ramadan 1446

println("Converted Gregorian: $customGregorian")
// Output: Converted Gregorian: 2025-03-10
```

### 5) Offset usage (+1 / -1)

```kotlin
val customHijri = MuslimCalendar(1446, 9, 10)
val offsetZero = customHijri.setOffset(0)
val offsetPlus = customHijri.setOffset(1)
val offsetMinus = customHijri.setOffset(-1)

println("Offset 0 : ${offsetZero.format("dd MMMM yyyy", Locale.ENGLISH)}")
// Output: Offset 0 : 10 Ramadan 1446

println("Offset +1: ${offsetPlus.format("dd MMMM yyyy", Locale.ENGLISH)}")
// Output: Offset +1: 11 Ramadan 1446

println("Offset -1: ${offsetMinus.format("dd MMMM yyyy", Locale.ENGLISH)}")
// Output: Offset -1: 09 Ramadan 1446

val base = MuslimCalendar(1446, 9, 1)
val adjusted = base.setOffset(1)

println(adjusted.format("dd MMMM yyyy", Locale.ENGLISH))
// Output: 02 Ramadan 1446
```

### 6) Localization (Arabic, Urdu)

```kotlin
val date = MuslimCalendar(1446, 9, 10)

println("English: ${date.getDayName(Locale.ENGLISH)}, ${date.getMonthName(Locale.ENGLISH)}")
// Output: English: Monday, Ramadan

println("Arabic : ${date.getDayName(Locale("ar"))}, ${date.getMonthName(Locale("ar"))}")
// Output: Arabic : الاثنين, رمضان

println("Urdu   : ${date.getDayName(Locale("ur", "PK"))}, ${date.getMonthName(Locale("ur", "PK"))}")
// Output: Urdu   : پیر, رمضان
```

### 7) Formatting

Supported tokens:

- `d`, `dd`
- `M`, `MM`, `MMM`, `MMMM`
- `y`, `yy`, `yyyy`
- `E`, `EEEE`

```kotlin
val date = MuslimCalendar(1446, 9, 10)

println("Pattern [dd MMMM yyyy]: ${date.format("dd MMMM yyyy", Locale.ENGLISH)}")
// Output: Pattern [dd MMMM yyyy]: 10 Ramadan 1446

println("Pattern [EEEE, d MMMM yyyy]: ${date.format("EEEE, d MMMM yyyy", Locale.ENGLISH)}")
// Output: Pattern [EEEE, d MMMM yyyy]: Monday, 10 Ramadan 1446
```

### 8) Parsing

```kotlin
val parseInput = "10 Ramadan 1446"
val parsed = MuslimCalendar.parse(
    input = parseInput,
    pattern = "dd MMMM yyyy",
    locale = Locale.ENGLISH
)

println("Input: $parseInput")
// Output: Input: 10 Ramadan 1446

println("Parsed: ${parsed.format("dd MMMM yyyy", Locale.ENGLISH)}")
// Output: Parsed: 10 Ramadan 1446
```

### 9) Month & Year info

```kotlin
val customHijri = MuslimCalendar(1446, 9, 10)

println("Length of month (${customHijri.month}/${customHijri.year}): ${MuslimCalendar.lengthOfMonth(customHijri.year, customHijri.month)} days")
// Output: Length of month (9/1446): 29 days

println("Length of year (${customHijri.year}): ${MuslimCalendar.lengthOfYear(customHijri.year)} days")
// Output: Length of year (1446): 354 days

println("Is leap year (${customHijri.year}): ${MuslimCalendar.isLeapYear(customHijri.year)}")
// Output: Is leap year (1446): false
```

---

## 🌍 Locale Support

- Supports **25+ languages**
- Includes **Arabic, English, and Urdu**
- Locale mappings are easy to extend through `LocaleProvider`
  
| Code | Language   | Code | Language    |
| ---- | ---------- | ---- | ----------- |
| en   | English    | ar   | Arabic      |
| ur   | Urdu       | id   | Indonesian  |
| hi   | Hindi      | bn   | Bengali     |
| fa   | Persian    | tr   | Turkish     |
| am   | Amharic    | fr   | French      |
| ms   | Malay      | az   | Azerbaijani |
| kk   | Kazakh     | ky   | Kyrgyz      |
| tg   | Tajik      | ru   | Russian     |
| de   | German     | sv   | Swedish     |
| th   | Thai       | fil  | Filipino    |
| ja   | Japanese   | es   | Spanish     |
| ko   | Korean     | sw   | Swahili     |
| pt   | Portuguese |      |             |


To add a new locale, provide:

- full month names
- short month names
- full day names
- short day names

## ⚠️ Limitations

- Valid only inside the supported Umm Al-Qura data range
- `offsetDays` is a manual post-conversion adjustment
- Not an astronomical moon-sighting engine
- Supported range: 1300H – 1600H

## Contributing
Contributions are welcome! Fork the repository, make changes, and submit a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

Copyright OrbitalSonic

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
