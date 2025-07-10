package com.bakery.core.state

/**
* Interface to represent the state of a request
* */
sealed interface RequestState<out T> {
    data class Success<T>(val data: T) : RequestState<T>
    data class Error(val error: String?) : RequestState<Nothing>
    data object Loading : RequestState<Nothing>
    data object Idle : RequestState<Nothing>
}
