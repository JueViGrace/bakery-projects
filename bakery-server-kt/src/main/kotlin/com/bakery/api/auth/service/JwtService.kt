package com.bakery.api.auth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.bakery.api.users.repository.UserRepository
import com.bakery.models.auth.Role
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import java.util.Date

class JwtService(
    private val secret: String,
    private val issuer: String,
    private val audience: String,
    val realm: String,
    private val userRepository: UserRepository,
) {
    companion object {
        private const val ACCESS_EXPIRES_IN = 3_600_000
        private const val REFRESH_EXPIRES_IN = 86_400_000
    }

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createAccessToken(id: Int, email: String, role: String): String =
        createJwtToken(id, email, role, ACCESS_EXPIRES_IN)

    fun createRefreshToken(id: Int, email: String, role: String): String =
        createJwtToken(id, email, role, REFRESH_EXPIRES_IN)

    private fun createJwtToken(id: Int, email: String, role: String, expiresIn: Int): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("user_id", id.toString())
            .withClaim("email", email)
            .withClaim("role", role)
            .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
            .sign(Algorithm.HMAC256(secret))

    suspend fun userValidator(credential: JWTCredential): JWTPrincipal? {
        val email = extractEmail(credential)
        val foundUser = email?.let { e -> userRepository.findOneByEmail(e) }

        return foundUser?.let {
            if (audienceMatches(credential)) {
                JWTPrincipal(credential.payload)
            } else {
                null
            }
        }
    }

    fun roleValidator(credential: JWTCredential): JWTPrincipal? {
        val role = extractRole(credential)
        val roleMatches = role?.let { it == Role.ADMIN.value } ?: false

        return if (roleMatches && audienceMatches(credential)) {
            JWTPrincipal(credential.payload)
        } else {
            null
        }
    }

    private fun audienceMatches(
        credential: JWTCredential,
    ): Boolean =
        credential.payload.audience.contains(audience)

    fun audienceMatches(
        audience: String
    ): Boolean =
        this.audience == audience

    private fun extractRole(credential: JWTCredential): String? =
        credential.payload.getClaim("role").asString()

    private fun extractEmail(credential: JWTCredential): String? =
        credential.payload.getClaim("email").asString()

    private fun extractId(credential: JWTCredential): String? =
        credential.payload.getClaim("user_id").asString()
}
