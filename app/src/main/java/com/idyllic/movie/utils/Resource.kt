package com.idyllic.movie.utils

sealed class Resource<out R> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Fail(val message: String) : Resource<Nothing>()
}
