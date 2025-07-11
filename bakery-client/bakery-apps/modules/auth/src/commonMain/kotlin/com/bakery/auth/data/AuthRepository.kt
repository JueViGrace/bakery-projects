package com.bakery.auth.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import com.bakery.network.model.ApiOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import migrations.Session as DBSession

interface AuthRepository : Repository {
    fun logout(): Flow<RequestState<Boolean>>
    fun refresh(): Flow<RequestState<Boolean>>
}

internal class DefaultAuthRepository(private val authClient: AuthClient, private val authHelper: AuthHelper) : AuthRepository {
    override fun logout(): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                val session = authHelper.getSession()
                    ?: return@startNetworkRequest ApiOperation.Failure(Exception("Session not found"))
                authClient.logout(session.accessToken)
            },
        ) { value ->
            emit(RequestState.Success(true))
        }
    }

    override fun refresh(): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                val session = authHelper.getSession()
                    ?: return@startNetworkRequest ApiOperation.Failure(Exception("Session not found"))
                authClient.refresh(session.refreshToken)
            },
        ) { value ->
            scope.launch {
                authHelper.createSession(
                    DBSession(
                        id = value.id,
                        accessToken = value.accessToken,
                        refreshToken = value.refreshToken,
                        active = true
                    )
                )
            }
            emit(RequestState.Success(true))
        }
    }
}
