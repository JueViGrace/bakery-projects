package com.bakery.common

import com.bakery.common.Constants.status
import com.bakery.models.response.AppResponse
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.util.toGMTDate
import io.ktor.util.date.toJvmDate
import io.ktor.util.pipeline.PipelineContext
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

fun Instant.toDate(): Date {
    return this.toGMTDate().toJvmDate()
}

fun String.toDate(): Date {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
}

fun String.toInstant(): Instant {
    return try {
        this.toDate().toInstant()
    } catch (e: IllegalArgumentException) {
        throw e
    }
}

fun String.validDate(): Boolean {
    return try {
        this.toDate()
        true
    } catch (e: IllegalFormatException) {
        false
        throw e
    }
}

suspend inline fun<reified T> ApplicationCall.applicationResponse(response: AppResponse<T>) {
    when (response) {
        is AppResponse.FailureResponse -> {
            respond(
                status = status.copy(
                    value = response.status,
                    description = response.description
                ),
                message = response
            )
        }
        is AppResponse.SuccessResponse -> {
            respond(
                status = status.copy(
                    value = response.status,
                    description = response.description
                ),
                message = response
            )
        }
    }
}

fun PipelineContext<Unit, ApplicationCall>.validateUserId(id: Int): Boolean {
    val principal = call.principal<JWTPrincipal>() ?: return false

    val userId = principal.payload.getClaim("user_id").asString()

    return userId.toInt() == id
}

fun PipelineContext<Unit, ApplicationCall>.extractUserIdFromPrincipal(): Int? {
    return call.principal<JWTPrincipal>()?.let {
        it.payload.getClaim("user_id")?.asString()?.toInt()
    }
}
