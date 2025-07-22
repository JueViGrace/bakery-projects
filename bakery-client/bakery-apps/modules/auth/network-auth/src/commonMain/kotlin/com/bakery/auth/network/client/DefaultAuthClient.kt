package com.bakery.auth.network.client

import com.bakery.auth.network.model.dto.ConfirmPasswordResetDto
import com.bakery.auth.network.model.dto.PasswordResetDto
import com.bakery.auth.network.model.dto.RequestPasswordResetDto
import com.bakery.auth.network.model.dto.SignInDto
import com.bakery.auth.network.model.dto.SignUpDto
import com.bakery.auth.network.model.response.AuthResponse
import com.bakery.network.client.base.NetworkClient
import com.bakery.network.client.base.post
import com.bakery.network.model.ApiOperation
import com.bakery.network.model.ApiResponse

class DefaultAuthClient(override val client: NetworkClient) : AuthClient {
    override suspend fun requestPasswordReset(
        dto: RequestPasswordResetDto
    ): ApiOperation<ApiResponse<String>> = client.post(
        urlString = "${NetworkClient.DEFAULT_API_PREFIX}/auth/reset/password/request",
        body = dto,
    )

    override suspend fun confirmPasswordReset(
        dto: ConfirmPasswordResetDto
    ): ApiOperation<ApiResponse<String>> = client.post(
        urlString = "${NetworkClient.DEFAULT_API_PREFIX}/auth/reset/password/confirm",
        body = dto,
    )

    override suspend fun resetPassword(dto: PasswordResetDto): ApiOperation<ApiResponse<String>> = client.post(
        body = dto,
        urlString = "${NetworkClient.DEFAULT_API_PREFIX}/auth/reset/password",
    )

    override suspend fun refresh(token: String): ApiOperation<ApiResponse<AuthResponse>> = client.post(
        urlString = "${NetworkClient.DEFAULT_API_PREFIX}/auth/refresh",
        headers = mapOf("Authorization" to "Bearer $token"),
    )

    override suspend fun logout(token: String): ApiOperation<ApiResponse<String>> = client.post(
        urlString = "${NetworkClient.DEFAULT_API_PREFIX}/auth/logout",
        body = null,
        headers = mapOf("Authorization" to "Bearer $token"),
    )

    override suspend fun logIn(dto: SignInDto): ApiOperation<ApiResponse<AuthResponse>> = client.post(
        urlString = "${NetworkClient.DEFAULT_API_PREFIX}/auth/login",
        body = dto,
    )

    override suspend fun signUp(dto: SignUpDto): ApiOperation<ApiResponse<AuthResponse>> = client.post(
        urlString = "${NetworkClient.DEFAULT_API_PREFIX}/auth/signup",
        body = dto,
    )
}
