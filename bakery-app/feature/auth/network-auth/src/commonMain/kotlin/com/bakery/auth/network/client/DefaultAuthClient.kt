package com.bakery.auth.network.client

import com.bakery.auth.network.model.dto.ConfirmPasswordResetDto
import com.bakery.auth.network.model.dto.LogInDto
import com.bakery.auth.network.model.dto.PasswordResetDto
import com.bakery.auth.network.model.dto.RequestPasswordResetDto
import com.bakery.auth.network.model.dto.SignUpDto
import com.bakery.auth.network.model.response.AuthResponse
import com.bakery.network.client.base.NetworkClient
import com.bakery.network.client.base.get
import com.bakery.network.client.base.post
import com.bakery.network.model.ApiOperation

class DefaultAuthClient(override val client: NetworkClient) : AuthClient {
    override suspend fun confirmPasswordReset(dto: ConfirmPasswordResetDto): ApiOperation<String> {
        return client.post(
            urlString = "/auth/forgot/confirm",
            body = dto,
        )
    }

    override suspend fun login(dto: LogInDto): ApiOperation<AuthResponse> {
        return client.post(
            urlString = "/auth/login",
            body = dto,
        )
    }

    override suspend fun logout(token: String): ApiOperation<String> {
        return client.post(
            urlString = "/auth/logout",
            body = null,
            headers = mapOf("Authorization" to "Bearer $token"),
        )
    }

    override suspend fun refresh(token: String): ApiOperation<AuthResponse> {
        return client.get(
            urlString = "/auth/refresh",
            headers = mapOf("Authorization" to "Bearer $token"),
        )
    }

    override suspend fun requestPasswordReset(dto: RequestPasswordResetDto): ApiOperation<String> {
        return client.post(
            urlString = "/auth/forgot/request",
            body = dto,
        )
    }

    override suspend fun resetPassword(dto: PasswordResetDto): ApiOperation<String> {
        return client.post(
            urlString = "/auth/forgot/reset",
            body = dto,
        )
    }

    override suspend fun signUp(dto: SignUpDto): ApiOperation<AuthResponse> {
        return client.post(
            urlString = "/auth/signin",
            body = dto,
        )
    }
}