package com.bakery.common

import io.ktor.http.HttpStatusCode
import io.ktor.server.util.toLocalDateTime
import io.ktor.util.InternalAPI
import java.util.Date

object Constants {
    const val UNEXPECTED_ERROR = "An unexpected error occurred"

    const val SALT_ROUNDS: Int = 10

    @OptIn(InternalAPI::class)
    val time = Date().toLocalDateTime().toString()

    private val emailRegex = "^[^\\.\\s][\\w\\-\\.{2,}]+@([\\w-]+\\.)+[\\w-]{2,}\$".toRegex()

    fun validEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    val status = HttpStatusCode(HttpStatusCode.OK.value, HttpStatusCode.OK.description)
}
