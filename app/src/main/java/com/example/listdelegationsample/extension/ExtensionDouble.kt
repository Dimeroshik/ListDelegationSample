package com.example.listdelegationsample.extension

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

fun Double.toAmount(
    currencyCode: String?,
    maxFractionDigits: Int = this.maxFractionDigits()
): String = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).apply {
        currency = Currency.getInstance(currencyCode ?: "RUB")
        when (maxFractionDigits) {
            0 -> {
                maximumFractionDigits = maxFractionDigits
                roundingMode = RoundingMode.DOWN
            }
            else -> {
                maximumFractionDigits = maxFractionDigits
            }
        }
    }.format(this)

fun Double.toAmountRound(
    currencyCode: String?,
    maxFractionDigits: Int = this.maxFractionDigits()
): String {
    return this.toAmount(currencyCode, if (this % 1.0 == 0.0) 0 else maxFractionDigits)
}

fun Double.maxFractionDigits() = when {
    this % 1.0 == 0.0 -> 0
    else -> 2
}

fun Double?.toAmountValue() = when {
    this == null -> ""
    else -> "%.${this.maxFractionDigits()}f".format(this).replace(".",",")
}