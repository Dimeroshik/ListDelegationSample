package com.example.listdelegationsample.extension

import android.text.format.DateUtils
import com.example.listdelegationsample.R
import com.example.listdelegationsample.model.data.network.adapter.DateAdapter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Date.isToday(): Boolean = DateUtils.isToday(this.time)

fun Date.isYesterday(): Boolean = DateUtils.isToday(this.time + DateUtils.DAY_IN_MILLIS)

fun Date.isTomorrow(): Boolean = DateUtils.isToday(this.time - DateUtils.DAY_IN_MILLIS)

fun Date.isDayAfterTomorrow(): Boolean = DateUtils.isToday(this.time - 2 * DateUtils.DAY_IN_MILLIS)

fun Date.format(pattern: String, locale: Locale = Locale("ru", "RU")): String =
    SimpleDateFormat(pattern, locale).format(this)

fun String.parse(pattern: String = DateAdapter.DATE_FORMAT_SECOND, locale: Locale = Locale("ru", "RU")) =
    SimpleDateFormat(pattern, locale).parse(this)

fun String.isValidDate(pattern: String, locale: Locale = Locale("ru", "RU")): Boolean {
    val sdf: DateFormat = SimpleDateFormat(pattern, locale)
    sdf.isLenient = false
    try {
        sdf.parse(this)
    } catch (e: ParseException) {
        return false
    }
    return true
}

fun String.parseUTC0(
    pattern: String = DateAdapter.DATE_FORMAT,
    locale: Locale = Locale("ru", "RU")
) =
    SimpleDateFormat(pattern, locale).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.parse(this)

fun Date.dayOfMonth(): Int =
    Calendar.getInstance().apply {
        time = this@dayOfMonth
    }.get(Calendar.DAY_OF_MONTH)

fun Date.dayOfWeek(locale: Locale = Locale("ru", "RU")): String? =
    Calendar.getInstance().apply {
        time = this@dayOfWeek
    }.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale)
        ?.toUpperCase(locale)

fun Date.toCalendar() = Calendar.getInstance().apply {
    time = this@toCalendar
}

fun Date.hourOfDay(): Int =
    toCalendar().get(Calendar.HOUR_OF_DAY)

fun Date.minute(): Int =
    toCalendar().get(Calendar.MINUTE)

fun Date.fullTimeFormat(): String = format("yyyy-MM-dd'T'HH:mm:ss'.000000Z'")

fun Date.plusFewSeconds(number: Int): Long =
    System.currentTimeMillis() / 1000 + number
