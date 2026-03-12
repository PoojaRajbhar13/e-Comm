package com.example.myecomartapp.core.util

sealed class Result<out T> {

    data class Success<T>(val data: T):  Result<T>()

    data object Loading: Result<Nothing>()

    data object Idle: Result<Nothing>()

    data class Failure(val message: String): Result<Nothing>()

}