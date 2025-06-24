package com.bakery.api.users.routes

import com.bakery.api.users.service.UserService
import com.bakery.common.applicationResponse
import com.bakery.models.auth.Role
import com.bakery.common.extractUserIdFromPrincipal
import com.bakery.models.response.DefaultHttpResponse
import com.bakery.models.user.UserDto
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy.Required
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.util.pipeline.PipelineContext
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    route("/users") {
        authenticate("user-auth", strategy = Required) {
            val userService: UserService by inject<UserService>()

            authenticate("role-auth", strategy = Required) {
                get {
                    val users = userService.getAllUsers()

                    call.applicationResponse(users)
                }

                delete {
                    val userId = extractUserIdFromPrincipal()
                        ?: return@delete call.respond(DefaultHttpResponse.badRequest())

                    val deletedUser = userService.deleteUser(userId)

                    call.applicationResponse(deletedUser)
                }
            }

            get("/{id}") {
                val idParam = call.parameters["id"]
                    ?: return@get call.respond(DefaultHttpResponse.badRequest("Parameter id must be provided"))

                if (idParam.map { it.isDigit() }.contains(false)) {
                    return@get call.respond(
                        DefaultHttpResponse.badRequest("Parameter id must be a valid number")
                    )
                }

                if (!validateSearch(idParam.toInt())) {
                    return@get call.respond(DefaultHttpResponse.forbidden("Forbidden action"))
                }

                val user = userService.getOneUser(idParam.toInt())

                call.applicationResponse(user)
            }

            put {
                val body = call.receive<UserDto>()

                if (!validateUpdate(body)) {
                    return@put call.respond(DefaultHttpResponse.forbidden("Forbidden action"))
                }

                val savedUser = userService.updateUser(body)

                call.applicationResponse(savedUser)
            }
        }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.validateUpdate(user: UserDto): Boolean {
    val principal = call.principal<JWTPrincipal>() ?: return false

    val id = principal.payload.getClaim("user_id").asString()
    val email = principal.payload.getClaim("email").asString()
    val role = principal.payload.getClaim("role").asString()

    return (email == user.email && id.toInt() == user.userId) || role == Role.ADMIN.value
}

private fun PipelineContext<Unit, ApplicationCall>.validateSearch(id: Int): Boolean {
    val principal = call.principal<JWTPrincipal>() ?: return false

    val userId = principal.payload.getClaim("user_id").asString()

    return userId.toInt() == id
}
