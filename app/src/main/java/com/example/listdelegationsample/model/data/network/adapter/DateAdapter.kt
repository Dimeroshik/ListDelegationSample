package com.example.listdelegationsample.model.data.network.adapter

import com.example.listdelegationsample.extension.format
import com.example.listdelegationsample.extension.parse
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class DateAdapter {
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        const val DATE_FORMAT_SECOND = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    }

    @FromJson
    fun stringToDate(data: String): Date? = try {
        when (data.length > DATE_FORMAT.length) {
            true -> {
                data.parse(DATE_FORMAT_SECOND)
            }
            else -> {
                data.parse(DATE_FORMAT)
            }
        }
    } catch (e: Exception) {
        null
    }

    @ToJson
    fun dateToString(data: Date?): String? =
        data?.format(DATE_FORMAT)
}