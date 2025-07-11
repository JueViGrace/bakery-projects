package com.bakery.auth.forgot.data

import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.network.model.dto.ConfirmPasswordResetDto
import com.bakery.auth.network.model.dto.RequestPasswordResetDto
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import com.bakery.types.auth.forgot.PasswordReset
import kotlinx.coroutines.flow.Flow

interface ForgotRepository : Repository {
    fun confirmPasswordReset(code: String): Flow<RequestState<Boolean>>
    fun requestPasswordReset(username: String): Flow<RequestState<Boolean>>
    fun resetPassword(reset: PasswordReset): Flow<RequestState<Boolean>>
}

class DefaultForgotRepository(private val client: AuthClient) : ForgotRepository {
    override fun confirmPasswordReset(code: String): Flow<RequestState<Boolean>> = startNetworkRequest(
        call = {
            client.confirmPasswordReset(
                ConfirmPasswordResetDto(
                    code = code,
                ),
            )
        },
    ) { value ->
        emit(RequestState.Success(true))
    }
    override fun requestPasswordReset(username: String): Flow<RequestState<Boolean>> = startNetworkRequest(
        call = {
            client.requestPasswordReset(
                RequestPasswordResetDto(
                    username = username,
                ),
            )
        },
    ) { value ->
        emit(RequestState.Success(true))
    }

    override fun resetPassword(reset: PasswordReset): Flow<RequestState<Boolean>> = startNetworkRequest(
        call = {
            client.resetPassword(reset.toDto())
        },
    ) { value ->
        emit(RequestState.Success(true))
    }
}
