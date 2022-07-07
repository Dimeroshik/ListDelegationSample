package com.example.listdelegationsample.entity.network.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.HttpURLConnection


sealed class Error(
    @Json(name = "status_code")
    var statusCode: Int = HttpURLConnection.HTTP_INTERNAL_ERROR
) {

    @JsonClass(generateAdapter = true)
    data class UnknownError(
        @Json(name = "description")
        val description: String? = null,
        @Json(name = "error")
        var errorItem: ErrorsItem? = null
    ) : Error()

    data class ListErrors(
        val code: Int,
        val errors: MutableList<ErrorsItem>
    ) : Error(code)
}

@JsonClass(generateAdapter = true)
data class ErrorsItem(
    @Json(name = "code")
    val code: String? = null,
    @Json(name = "field")
    val field: String? = null,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "error")
    var codeText: String? = null
)


enum class ResultCode(val value: String) {

    @Json(name = "unknown")
    UNKNOWN("unknown");

    companion object {
        fun from(value: String?): ResultCode = values().find { it.value == value } ?: UNKNOWN
    }
}