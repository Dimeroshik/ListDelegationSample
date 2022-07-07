package com.example.listdelegationsample.entity.network.base

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val error: Error) : Result<Nothing>()
    data class Exclusion(val exception: Exception) : Result<Nothing>()
    data class InternetConnectionError(val exception: Exception) : Result<Nothing>()
}