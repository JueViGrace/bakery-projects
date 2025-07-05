package com.bakery.types.common

fun String.isValidInt(): String? {
    return if (this.any { !it.isDigit() }) {
        null
    } else {
        this.ifEmpty {
            ""
        }
    }
}
