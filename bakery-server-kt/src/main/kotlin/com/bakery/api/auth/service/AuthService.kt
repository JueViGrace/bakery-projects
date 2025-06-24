package com.bakery.api.auth.service

import com.auth0.jwt.interfaces.DecodedJWT
import com.bakery.common.Constants.SALT_ROUNDS
import com.bakery.api.auth.repository.RefreshTokenRepository
import com.bakery.api.cart.repository.CartRepository
import com.bakery.api.users.repository.UserRepository
import com.bakery.models.auth.AuthResponse
import com.bakery.models.auth.LoginDto
import com.bakery.models.auth.RegisterDto
import com.bakery.models.auth.Token
import com.bakery.models.auth.toDatabase
import com.bakery.models.cart.CartDto
import com.bakery.models.cart.toDatabase
import com.bakery.models.response.AppResponse
import com.bakery.models.response.DefaultHttpResponse
import com.bakery.models.user.toInsert
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.withContext
import java.nio.charset.Charset
import kotlin.coroutines.CoroutineContext

class AuthService(
    private val coroutineContext: CoroutineContext,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val cartRepository: CartRepository,
    private val jwtService: JwtService
) {
    suspend fun register(registerDto: RegisterDto): AppResponse<AuthResponse?> {
        return try {
            withContext(coroutineContext) {
                val existingUser = userRepository.findOneByEmail(registerDto.email)

                if (existingUser != null) {
                    return@withContext DefaultHttpResponse.conflict(
                        "A user with email ${existingUser.email} already exists"
                    )
                }

                val hashedPass = Bcrypt.hash(registerDto.password, SALT_ROUNDS).toString(Charset.defaultCharset())

                val savedUser = userRepository.insert(registerDto.copy(password = hashedPass).toInsert())

                if (savedUser != null) {
                    val accessToken = jwtService.createAccessToken(
                        id = savedUser.id,
                        email = savedUser.email,
                        role = savedUser.role
                    )
                    val refreshToken = jwtService.createRefreshToken(
                        id = savedUser.id,
                        email = savedUser.email,
                        role = savedUser.role
                    )

                    refreshTokenRepository.insert(Token(token = refreshToken, email = savedUser.email).toDatabase())

                    cartRepository.insert(CartDto(id = savedUser.id).toDatabase())

                    DefaultHttpResponse.created(
                        AuthResponse(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    )
                } else {
                    DefaultHttpResponse.notFound("Unable to create user")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun login(loginDto: LoginDto): AppResponse<AuthResponse?> {
        return try {
            withContext(coroutineContext) {
                val user = userRepository.findOneByEmail(loginDto.email)
                    ?: return@withContext DefaultHttpResponse.notFound(
                        "User with email ${loginDto.email} was not found"
                    )

                if (Bcrypt.verify(loginDto.password, user.password.toByteArray())) {
                    val accessToken = jwtService.createAccessToken(id = user.id, email = user.email, role = user.role)
                    val refreshToken = jwtService.createRefreshToken(id = user.id, email = user.email, role = user.role)

                    if (refreshTokenRepository.findTokenByEmail(loginDto.email) == null) {
                        refreshTokenRepository.insert(Token(token = refreshToken, email = user.email).toDatabase())
                    } else {
                        refreshTokenRepository.updateToken(Token(token = refreshToken, email = user.email).toDatabase())
                    }

                    DefaultHttpResponse.ok(
                        AuthResponse(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    )
                } else {
                    DefaultHttpResponse.badRequest(message = "Invalid credentials")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun refreshToken(token: String): AppResponse<AuthResponse?> {
        val decodedRefreshToken = verifyRefreshToken(token)
        val persistedEmail = refreshTokenRepository.findEmailByToken(token)?.user_email

        return if (decodedRefreshToken != null && persistedEmail != null) {
            val foundUser = userRepository.findOneByEmail(persistedEmail)
            val emailFromToken = decodedRefreshToken.getClaim("email").asString()

            if (foundUser != null && emailFromToken == foundUser.email) {
                val newRefreshToken = jwtService.createRefreshToken(
                    id = foundUser.id,
                    email = foundUser.email,
                    role = foundUser.role
                )
                val newToken = jwtService.createAccessToken(
                    id = foundUser.id,
                    email = foundUser.email,
                    role = foundUser.role
                )

                refreshTokenRepository.updateToken(Token(token = newRefreshToken, email = foundUser.email).toDatabase())

                DefaultHttpResponse.accepted(
                    AuthResponse(
                        accessToken = newToken,
                        refreshToken = newRefreshToken
                    )
                )
            } else {
                DefaultHttpResponse.unauthorized(message = "Invalid token")
            }
        } else {
            DefaultHttpResponse.badRequest(message = "Failed to authenticate, please log in again")
        }
    }

    private fun verifyRefreshToken(token: String): DecodedJWT? {
        val decodedJWT: DecodedJWT? = getDecodedJwt(token)

        return decodedJWT?.let { t ->
            val audienceMatches = jwtService.audienceMatches(t.audience.first())

            if (audienceMatches) {
                decodedJWT
            } else {
                null
            }
        }
    }

    private fun getDecodedJwt(token: String): DecodedJWT? {
        return try {
            jwtService.jwtVerifier.verify(token)
        } catch (e: Exception) {
            null
        }
    }
}
