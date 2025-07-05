package com.bakery.auth.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.domain.model.LogInForm
import com.bakery.auth.domain.model.PasswordReset
import com.bakery.auth.domain.model.SignUpForm
import com.bakery.auth.domain.model.mappers.database.toSession
import com.bakery.auth.domain.model.mappers.network.toSession
import com.bakery.auth.domain.model.mappers.toDbSession
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.network.model.dto.ConfirmPasswordResetDto
import com.bakery.auth.network.model.dto.RequestPasswordResetDto
import com.bakery.network.model.ApiOperation
import com.bakery.types.auth.Session
import com.bakery.types.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal class DefaultAuthRepository(
    private val authClient: AuthClient,
    private val authHelper: AuthHelper,
) : AuthRepository {
    override fun confirmPasswordReset(code: String): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                authClient.confirmPasswordReset(
                    ConfirmPasswordResetDto(
                        code = code
                    )
                )
            }
        ) { value ->
            emit(RequestState.Success(true))
        }
    }

    override fun login(logInForm: LogInForm): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                authClient.login(logInForm.toDto())
            }
        ) { value ->
            scope.launch {
                updateSession(value.toSession().copy(active = true))
            }
            emit(RequestState.Success(true))
        }
    }

    override fun logout(): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                val session = authHelper.getSession()?.toSession()
                    ?: return@startNetworkRequest ApiOperation.Failure(Exception("Session not found"))
                authClient.logout(session.accessToken)
            }
        ) { value ->
            emit(RequestState.Success(true))
        }
    }

    override fun requestPasswordReset(username: String): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                authClient.requestPasswordReset(
                    RequestPasswordResetDto(
                        username = username
                    )
                )
            }
        ) { value ->
            emit(RequestState.Success(true))
        }
    }

    override fun resetPassword(reset: PasswordReset): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                authClient.resetPassword(reset.toDto())
            }
        ) { value ->
            emit(RequestState.Success(true))
        }
    }

    override fun signUp(signUpForm: SignUpForm): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                authClient.signUp(signUpForm.toDto())
            }
        ) { value ->
            scope.launch {
                updateSession(value.toSession().copy(active = true))
            }
            emit(RequestState.Success(true))
        }
    }

    override fun refresh(): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                val session = authHelper.getSession()?.toSession()
                    ?: return@startNetworkRequest ApiOperation.Failure(Exception("Session not found"))
                authClient.refresh(session.refreshToken)
            }
        ) { value ->
            scope.launch {
                updateSession(value.toSession().copy(active = true))
            }
            emit(RequestState.Success(true))
        }
    }

    private suspend fun updateSession(session: Session) {
        authHelper.createSession(session.toDbSession())
    }
}
