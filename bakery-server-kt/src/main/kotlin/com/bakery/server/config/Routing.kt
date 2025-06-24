package com.bakery.server.config

import com.bakery.models.response.DefaultHttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("Routing")

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            logger.error("Unhandled error", cause)
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = DefaultHttpResponse.internalServerError(path = call.request.path())
            )
        }

        exception<RequestValidationException> { call, cause ->
            logger.error("Validation error", cause)
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = DefaultHttpResponse.badRequest(
                    message = cause.reasons.joinToString(),
                    path = call.request.path()
                )
            )
        }

        exception<BadRequestException> { call, cause ->
            logger.error(cause.message, cause)
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = DefaultHttpResponse.badRequest(
                    path = call.request.path()
                ),
            )
        }

        status(HttpStatusCode.Unauthorized) { call, status ->
            logger.error("Authorization error")
            call.respond(
                status = status,
                message = DefaultHttpResponse.unauthorized(path = call.request.path())
            )
        }

        status(HttpStatusCode.UnsupportedMediaType) { call, status ->
            logger.error("Media error")
            call.respond(
                status = status,
                message = DefaultHttpResponse.badRequest(
                    error = "The provided request body is invalid",
                    path = call.request.path()
                )
            )
        }

        status(HttpStatusCode.MethodNotAllowed) { call, status ->
            call.respond(
                status = status,
                message = DefaultHttpResponse.methodNotAllowed(
                    error = "Method ${call.request.httpMethod.value} is not allowed",
                    path = call.request.path()
                )
            )
        }
    }
}
