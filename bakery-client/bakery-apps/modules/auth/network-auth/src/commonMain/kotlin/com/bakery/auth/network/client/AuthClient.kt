package com.bakery.auth.network.client

import com.bakery.auth.network.model.dto.ConfirmPasswordResetDto
import com.bakery.auth.network.model.dto.PasswordResetDto
import com.bakery.auth.network.model.dto.RequestPasswordResetDto
import com.bakery.auth.network.model.dto.SignInDto
import com.bakery.auth.network.model.dto.SignUpDto
import com.bakery.auth.network.model.response.AuthResponse
import com.bakery.network.RemoteDataSource
import com.bakery.network.model.ApiOperation
import com.bakery.network.model.ApiResponse

interface AuthClient : RemoteDataSource {
    suspend fun confirmPasswordReset(dto: ConfirmPasswordResetDto): ApiOperation<ApiResponse<String>>
    suspend fun requestPasswordReset(dto: RequestPasswordResetDto): ApiOperation<ApiResponse<String>>
    suspend fun resetPassword(dto: PasswordResetDto): ApiOperation<ApiResponse<String>>
    suspend fun refresh(token: String): ApiOperation<ApiResponse<AuthResponse>>
    suspend fun logout(token: String): ApiOperation<ApiResponse<String>>
    suspend fun logIn(dto: SignInDto): ApiOperation<ApiResponse<AuthResponse>>
    suspend fun signUp(dto: SignUpDto): ApiOperation<ApiResponse<AuthResponse>>
}
