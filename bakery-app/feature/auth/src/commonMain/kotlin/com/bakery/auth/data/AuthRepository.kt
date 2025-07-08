package com.bakery.auth.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.types.mappers.database.toSession
import com.bakery.auth.types.mappers.network.toSession
import com.bakery.auth.types.mappers.toDbSession
import com.bakery.network.model.ApiOperation
import com.bakery.types.auth.Session
import com.bakery.types.data.Repository
import com.bakery.types.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface AuthRepository : Repository {
    fun logout(): Flow<RequestState<Boolean>>
    fun refresh(): Flow<RequestState<Boolean>>
}

internal class DefaultAuthRepository(
    private val authClient: AuthClient,
    private val authHelper: AuthHelper,
) : AuthRepository {

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
