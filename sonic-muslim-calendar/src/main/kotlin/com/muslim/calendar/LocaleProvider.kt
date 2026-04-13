package com.muslim.calendar

import java.time.DayOfWeek
import java.util.Locale

object LocaleProvider {
    private val defaultMonths = listOf(
        "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
        "Jumada al-Ula", "Jumada al-Akhirah", "Rajab", "Sha'ban",
        "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
    )
    private val defaultShortMonths = listOf(
        "Muh", "Saf", "Rab-I", "Rab-II", "Jum-I", "Jum-II",
        "Raj", "Sha", "Ram", "Shw", "Dhu-Q", "Dhu-H"
    )
    private val defaultDays = listOf(
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    )
    private val defaultShortDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    private val monthNames = mapOf(
        "en" to defaultMonths,
        "ar" to listOf("محرم", "صفر", "ربيع الأول", "ربيع الثاني", "جمادى الأولى", "جمادى الآخرة", "رجب", "شعبان", "رمضان", "شوال", "ذو القعدة", "ذو الحجة"),
        "ur" to listOf("محرم", "صفر", "ربیع الاول", "ربیع الثانی", "جمادی الاول", "جمادی الثانی", "رجب", "شعبان", "رمضان", "شوال", "ذوالقعدہ", "ذوالحجہ"),
        "id" to listOf("Muharam", "Safar", "Rabiulawal", "Rabiulakhir", "Jumadilawal", "Jumadilakhir", "Rajab", "Syaban", "Ramadan", "Syawal", "Zulkaidah", "Zulhijah"),
        "hi" to listOf("मुहर्रम", "सफ़र", "रबीउल अव्वल", "रबीउल आखिर", "जुमादा अल-अव्वल", "जुमादा अल-आखिर", "रजब", "शाबान", "रमज़ान", "शव्वाल", "ज़ुल क़ादा", "ज़ुल हिज्जा"),
        "bn" to listOf("মুহররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি", "জুমাদাল উলা", "জুমাদাস সানিয়া", "রজব", "শাবান", "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ"),
        "fa" to listOf("محرم", "صفر", "ربیع‌الاول", "ربیع‌الثانی", "جمادی‌الاول", "جمادی‌الثانی", "رجب", "شعبان", "رمضان", "شوال", "ذیقعده", "ذیحجه"),
        "tr" to listOf("Muharrem", "Safer", "Rebiülevvel", "Rebiülahir", "Cemaziyelevvel", "Cemaziyelahir", "Recep", "Şaban", "Ramazan", "Şevval", "Zilkade", "Zilhicce"),
        "am" to listOf("ሙሐረም", "ሰፈር", "ረቢዕ አል አወል", "ረቢዕ አል ሳኒ", "ጁማዳ አል ኡላ", "ጁማዳ አል አኺራ", "ረጀብ", "ሻዕባን", "ረመዳን", "ሸዋል", "ዙልቃዕዳ", "ዙልሒጃ"),
        "fr" to listOf("Mouharram", "Safar", "ربيع الأول", "ربيع الثاني", "Joumada al oula", "Joumada al akhirah", "Rajab", "Chaabane", "Ramadan", "Chawwal", "Dhou al qi`da", "Dhou al hijja"),
        "ms" to listOf("Muharam", "Safar", "Rabiulawal", "Rabiulakhir", "Jamadilawal", "Jamadilakhir", "Rejab", "Syaaban", "Ramadan", "Syawal", "Zulkaedah", "Zulhijah"),
        "az" to listOf("Məhərrəm", "Səfər", "Rəbiüləvvəl", "Rəbiülaxir", "Cəmadiyələvvəl", "Cəmadiyəlaxir", "Rəcəb", "Şaban", "Ramazan", "Şəvval", "Zilqədə", "Zilhiccə"),
        "kk" to listOf("Мұхаррам", "Сафар", "Раби әл-әууәл", "Раби ас-сани", "Жумәда әл-әууәл", "Жумәда ас-сани", "Рәжәб", "Шағбан", "Рамазан", "Шәууәл", "Зүлқағда", "Зүлхижжа"),
        "ky" to listOf("Мухаррам", "Сафар", "Рабиул-аввал", "Рабиус-сани", "Жумада-ул-аввал", "Жумада-ус-сани", "Ражаб", "Шаабан", "Рамазан", "Шаввал", "Зул-каада", "Зул-хижжа"),
        "tg" to listOf("Муҳаррам", "Сафар", "Рабиъу-л-аввал", "Рабиъу-с-сонӣ", "Ҷумодо-л-аввал", "Ҷумодо-с-сонӣ", "Раҷаб", "Шаъбон", "Рамазон", "Шаввол", "Зулқаъда", "Зулҳиҷҷа"),
        "ru" to listOf("Мухаррам", "Сафар", "Раби аль-авваль", "Раби ас-сани", "Джумада аль-уля", "Джумада аль-ахира", "Раджаб", "Шаабан", "Рамадан", "Шавваль", "Зуль-каада", "Зуль-хиджа"),
        "de" to listOf("Muharram", "Safar", "Rabi al-awwal", "Rabi ath-thani", "Dschumada al-ula", "Dschumada al-akhira", "Radschab", "Schaban", "Ramadan", "Schawwal", "Dhu l-qiʿda", "Dhu l-hiddscha"),
        "sv" to listOf("Muharram", "Safar", "Rabi al-awwal", "Rabi al-thani", "Jumada al-ula", "Jumada al-akhira", "Rajab", "Sha'ban", "Ramadan", "Shawwal", "Dhu al-qi'dah", "Dhu al-hijjah"),
        "th" to listOf("มุฮัรรอม", "ซอฟัร", "รอบีอุลเอาวัล", "รอบีอุซซานี", "ญุมาดาอัลอูลา", "ญุมาดาอัซซานียะห์", "รอญับ", "ชะอ์บาน", "รอมฎอน", "เชาวาล", "ซุลเกาะอ์ดะฮ์", "ซุลฮิจญะฮ์"),
        "fil" to listOf("Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani", "Jumada al-Ula", "Jumada al-Akhirah", "Rajab", "Sha'ban", "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"),
        "ja" to listOf("ムハッラム", "サファル", "ラビー・アル＝アウワル", "ラビー・アッ＝サーニー", "ジュマーダー・アル＝ウーラー", "ジュマーダー・アル＝アーヒラ", "ラジャブ", "シャアバーン", "ラマダーン", "シャウワール", "ズー・アル＝カアダ", "ズー・アル＝ヒッジャ"),
        "es" to listOf("Muharram", "Safar", "Rabi al-awwal", "Rabi al-thani", "Yumada al-ula", "Yumada al-akhirah", "Rayab", "Sha'ban", "Ramadán", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"),
        "ko" to listOf("무하람", "사파르", "라비 알 아왈", "라비 알 타니", "주마다 알 울라", "주마다 알 아키라", "라자브", "샤아반", "라마단", "샤왈", "둘 카이다", "둘 힛자"),
        "sw" to listOf("Muharram", "Safar", "Rabiul Awwal", "Rabiul Thani", "Jumadal Ula", "Jumadal Akhira", "Rajab", "Shaaban", "Ramadhani", "Shawwal", "Dhul Qaada", "Dhul Hijja"),
        "pt" to listOf("Muharram", "Safar", "Rabi al-awwal", "Rabi al-thani", "Jumada al-ula", "Jumada al-akhirah", "Rajab", "Sha'ban", "Ramadão", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah")
    )

    private val monthShortNames = mapOf(
        "en" to defaultShortMonths,
        "ar" to listOf("محرم", "صفر", "ربيع 1", "ربيع 2", "جمادى 1", "جمادى 2", "رجب", "شعبان", "رمضان", "شوال", "ذو القعدة", "ذو الحجة"),
        "ur" to listOf("محرم", "صفر", "ربیع 1", "ربیع 2", "جمادی 1", "جمادی 2", "رجب", "شعبان", "رمضان", "شوال", "ذوالقعدہ", "ذوالحجہ"),
        "id" to listOf("Muh", "Saf", "Rab1", "Rab2", "Jum1", "Jum2", "Raj", "Sya", "Ram", "Syw", "Zqa", "Zhj"),
        "hi" to listOf("मुह", "सफ़", "रअ1", "रअ2", "जअ1", "जअ2", "रज", "शा", "रम", "शव", "ज़ुका", "ज़ुहि"),
        "bn" to listOf("মুহ", "সফ", "রবি1", "রবি2", "জুম1", "জুম2", "রজ", "শা", "রম", "শাও", "জিলক", "জিলহ"),
        "fa" to listOf("مح", "صف", "رب۱", "رب۲", "جم۱", "جم۲", "رجب", "شعب", "رمض", "شوال", "ذق", "ذح"),
        "tr" to listOf("Muh", "Saf", "Reb1", "Reb2", "Cem1", "Cem2", "Rec", "Şab", "Ram", "Şev", "ZilK", "ZilH"),
        "am" to listOf("ሙሐ", "ሰፈ", "ረቢ1", "ረቢ2", "ጁማ1", "ጁማ2", "ረጀ", "ሻዕ", "ረመ", "ሸዋ", "ዙቃ", "ዙሒ"),
        "fr" to listOf("Mou", "Saf", "Rab1", "Rab2", "Jou1", "Jou2", "Raj", "Cha", "Ram", "ChaW", "DQi", "DHj"),
        "ms" to listOf("Muh", "Saf", "Rab1", "Rab2", "Jam1", "Jam2", "Rej", "Sya", "Ram", "Syw", "ZulK", "ZulH"),
        "az" to listOf("Müh", "Səf", "Rəb1", "Rəb2", "Cəm1", "Cəm2", "Rəc", "Şab", "Ram", "Şəv", "ZilQ", "ZilH"),
        "kk" to listOf("Мұх", "Саф", "Раб1", "Раб2", "Жұм1", "Жұм2", "Рәж", "Шағ", "Рам", "Шәү", "ЗүлҚ", "ЗүлХ"),
        "ky" to listOf("Мух", "Саф", "Раб1", "Раб2", "Жум1", "Жум2", "Раж", "Шаа", "Рам", "Шав", "ЗулК", "ЗулХ"),
        "tg" to listOf("Муҳ", "Саф", "Раб1", "Раб2", "Ҷум1", "Ҷум2", "Раҷ", "Шаъ", "Рам", "Шав", "ЗулҚ", "ЗулҲ"),
        "ru" to listOf("Мух", "Саф", "Раб1", "Раб2", "Джу1", "Джу2", "Рад", "Шаа", "Рам", "Шав", "ЗулК", "ЗулХ"),
        "de" to listOf("Muh", "Saf", "Rab1", "Rab2", "Dju1", "Dju2", "Rad", "Sch", "Ram", "Shw", "DhuQ", "DhuH"),
        "sv" to listOf("Muh", "Saf", "Rab1", "Rab2", "Jum1", "Jum2", "Raj", "Sha", "Ram", "Shw", "DhuQ", "DhuH"),
        "th" to listOf("มุฮ", "ซอฟ", "รอ1", "รอ2", "ญุ1", "ญุ2", "รญ", "ชา", "รม", "ชว", "ซกอ", "ซฮจ"),
        "fil" to listOf("Muh", "Saf", "Rab1", "Rab2", "Jum1", "Jum2", "Raj", "Sha", "Ram", "Shw", "DhuQ", "DhuH"),
        "ja" to listOf("ムハ", "サフ", "ラビ1", "ラビ2", "ジュ1", "ジュ2", "ラジ", "シャ", "ラマ", "シャW", "ズルQ", "ズルH"),
        "es" to listOf("Muh", "Saf", "Rab1", "Rab2", "Yum1", "Yum2", "Ray", "Sha", "Ram", "Shw", "DhuQ", "DhuH"),
        "ko" to listOf("무하", "사파", "라비1", "라비2", "주마1", "주마2", "라자", "샤반", "라마", "샤왈", "둘카", "둘힛"),
        "sw" to listOf("Muh", "Saf", "Rab1", "Rab2", "Jum1", "Jum2", "Raj", "Sha", "Ram", "Shw", "DhuQ", "DhuH"),
        "pt" to listOf("Muh", "Saf", "Rab1", "Rab2", "Jum1", "Jum2", "Raj", "Sha", "Ram", "Shw", "DhuQ", "DhuH")
    )

    private val dayNames = mapOf(
        "en" to defaultDays,
        "ar" to listOf("الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت", "الأحد"),
        "ur" to listOf("پیر", "منگل", "بدھ", "جمعرات", "جمعہ", "ہفتہ", "اتوار"),
        "id" to listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"),
        "hi" to listOf("सोमवार", "मंगलवार", "बुधवार", "गुरुवार", "शुक्रवार", "शनिवार", "रविवार"),
        "bn" to listOf("সোমবার", "মঙ্গলবার", "বুধবার", "বৃহস্পতিবার", "শুক্রবার", "শনিবার", "রবিবার"),
        "fa" to listOf("دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه", "شنبه", "یکشنبه"),
        "tr" to listOf("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"),
        "am" to listOf("ሰኞ", "ማክሰኞ", "ረቡዕ", "ሐሙስ", "ዓርብ", "ቅዳሜ", "እሑድ"),
        "fr" to listOf("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"),
        "ms" to listOf("Isnin", "Selasa", "Rabu", "Khamis", "Jumaat", "Sabtu", "Ahad"),
        "az" to listOf("Bazar ertəsi", "Çərşənbə axşamı", "Çərşənbə", "Cümə axşamı", "Cümə", "Şənbə", "Bazar"),
        "kk" to listOf("Дүйсенбі", "Сейсенбі", "Сәрсенбі", "Бейсенбі", "Жұма", "Сенбі", "Жексенбі"),
        "ky" to listOf("Дүйшөмбү", "Шейшемби", "Шаршемби", "Бейшемби", "Жума", "Ишемби", "Жекшемби"),
        "tg" to listOf("Душанбе", "Сешанбе", "Чоршанбе", "Панҷшанбе", "Ҷумъа", "Шанбе", "Якшанбе"),
        "ru" to listOf("понедельник", "вторник", "среда", "четверг", "пятница", "суббота", "воскресенье"),
        "de" to listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"),
        "sv" to listOf("måndag", "tisdag", "onsdag", "torsdag", "fredag", "lördag", "söndag"),
        "th" to listOf("วันจันทร์", "วันอังคาร", "วันพุธ", "วันพฤหัสบดี", "วันศุกร์", "วันเสาร์", "วันอาทิตย์"),
        "fil" to listOf("Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo"),
        "ja" to listOf("月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日", "日曜日"),
        "es" to listOf("lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"),
        "ko" to listOf("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"),
        "sw" to listOf("Jumatatu", "Jumanne", "Jumatano", "Alhamisi", "Ijumaa", "Jumamosi", "Jumapili"),
        "pt" to listOf("segunda-feira", "terça-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sábado", "domingo")
    )

    private val dayShortNames = mapOf(
        "en" to defaultShortDays,
        "ar" to listOf("اثن", "ثلا", "أرب", "خمي", "جمع", "سبت", "أحد"),
        "ur" to listOf("پیر", "منگ", "بدھ", "جمع", "جمعہ", "ہفتہ", "ات"),
        "id" to listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"),
        "hi" to listOf("सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि", "रवि"),
        "bn" to listOf("সোম", "মঙ্গল", "বুধ", "বৃহ", "শুক্র", "শনি", "রবি"),
        "fa" to listOf("دو", "سه", "چه", "پن", "جم", "شن", "یک"),
        "tr" to listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz"),
        "am" to listOf("ሰኞ", "ማክ", "ረቡ", "ሐሙ", "ዓር", "ቅዳ", "እሑ"),
        "fr" to listOf("lun", "mar", "mer", "jeu", "ven", "sam", "dim"),
        "ms" to listOf("Isn", "Sel", "Rab", "Kha", "Jum", "Sab", "Ahd"),
        "az" to listOf("B.e", "Ç.a", "Çər", "C.a", "Cüm", "Şən", "Baz"),
        "kk" to listOf("Дүй", "Сей", "Сәр", "Бей", "Жұм", "Сен", "Жек"),
        "ky" to listOf("Дүй", "Шей", "Шар", "Бей", "Жум", "Ише", "Жек"),
        "tg" to listOf("Душ", "Сеш", "Чор", "Пан", "Ҷум", "Шан", "Якш"),
        "ru" to listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс"),
        "de" to listOf("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"),
        "sv" to listOf("mån", "tis", "ons", "tor", "fre", "lör", "sön"),
        "th" to listOf("จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส.", "อา."),
        "fil" to listOf("Lun", "Mar", "Miy", "Huw", "Biy", "Sab", "Lin"),
        "ja" to listOf("月", "火", "水", "木", "金", "土", "日"),
        "es" to listOf("lun", "mar", "mié", "jue", "vie", "sáb", "dom"),
        "ko" to listOf("월", "화", "수", "목", "금", "토", "일"),
        "sw" to listOf("J3", "J4", "J5", "Alh", "Iju", "J1", "J2"),
        "pt" to listOf("seg", "ter", "qua", "qui", "sex", "sáb", "dom")
    )

    fun monthName(month: Int, locale: Locale, short: Boolean = false): String =
        resolve(locale, if (short) monthShortNames else monthNames)[month - 1]

    fun dayName(dayOfWeek: DayOfWeek, locale: Locale, short: Boolean = false): String =
        resolve(locale, if (short) dayShortNames else dayNames)[dayOfWeek.ordinal]

    private fun resolve(locale: Locale, map: Map<String, List<String>>): List<String> {
        return map[locale.language.lowercase()] ?: map["en"] ?: error("English locale data missing")
    }
}
