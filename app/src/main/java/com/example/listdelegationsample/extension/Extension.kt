package com.example.listdelegationsample.extension

import android.os.CountDownTimer
import android.util.Log
import android.widget.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

suspend fun <T> withMain(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Main, block)

fun getDateString(date: String): String {
    val year = date.substring(0..3)
    val day = date.substring(8..9).toInt()
    val month = getMonthFromInt(date.substring(5..6).toInt())

    return "$day $month $year"
}

fun getDateStringNext(nextDate: String): String{
    var calendar = nextDate.parse().toCalendar()
    var month = getMonthFromInt(calendar.get(Calendar.MONTH))
    var day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$day $month"
}

fun getMonthFromInt(month: Int) = when (month) {
    1 -> "января"
    2 -> "февраля"
    3 -> "марта"
    4 -> "апреля"
    5 -> "мая"
    6 -> "июня"
    7 -> "июля"
    8 -> "августа"
    9 -> "сентября"
    10 -> "октября"
    11 -> "ноября"
    12 -> "декабря"
    else -> ""
}

fun getDate(date: String): String {
    val year = date.substring(0..3)
    val day = date.substring(8..9)
    val month = date.substring(5..6)

    return "$day.$month.$year"
}

fun ProgressBar.startProgress(
    timeInMillis: Long,
    onFinish: () -> Unit
): CountDownTimer {
    progress = 0
    var timer = object : CountDownTimer(timeInMillis, 20) {
        override fun onTick(millisUntilFinished: Long) {
            progress = ((timeInMillis - millisUntilFinished) * 240 / timeInMillis).toInt()
            Log.d("S_Timer", progress.toString())

        }

        override fun onFinish() {
            progress = max
            onFinish.invoke()
        }
    }

    timer.start()

    return timer
}


