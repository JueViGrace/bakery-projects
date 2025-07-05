package com.bakery.network.model

/**
* Represents a network request operation.
* */
sealed interface ApiOperation<out T> {
    data class Success<T>(val value: T) : ApiOperation<T>

    data class Failure(val exception: Exception) : ApiOperation<Nothing>
}
