package com.bakery.auth.network.client

import com.bakery.auth.network.model.dto.ConfirmPasswordResetDto
import com.bakery.auth.network.model.dto.LogInDto
import com.bakery.auth.network.model.dto.PasswordResetDto
import com.bakery.auth.network.model.dto.RequestPasswordResetDto
import com.bakery.auth.network.model.dto.SignUpDto
import com.bakery.auth.network.model.response.AuthResponse
import com.bakery.network.RemoteDataSource
import com.bakery.network.model.ApiOperation

interface AuthClient : RemoteDataSource {
    suspend fun confirmPasswordReset(dto: ConfirmPasswordResetDto): ApiOperation<String>
    suspend fun login(dto: LogInDto): ApiOperation<AuthResponse>
    suspend fun logout(token: String): ApiOperation<String>
    suspend fun refresh(token: String): ApiOperation<AuthResponse>
    suspend fun requestPasswordReset(dto: RequestPasswordResetDto): ApiOperation<String>
    suspend fun resetPassword(dto: PasswordResetDto): ApiOperation<String>
    suspend fun signUp(dto: SignUpDto): ApiOperation<AuthResponse>
}

