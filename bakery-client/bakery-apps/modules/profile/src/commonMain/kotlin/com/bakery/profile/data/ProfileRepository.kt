package com.bakery.profile.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import com.bakery.network.model.ApiOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface ProfileRepository : Repository {
    fun logOut(): Flow<RequestState<Boolean>>
}

class DefaultProfileRepository(
    private val authClient: AuthClient,
    private val authHelper: AuthHelper,
) : ProfileRepository {
    override fun logOut(): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                val session = authHelper.getSession()
                    ?: return@startNetworkRequest ApiOperation.Failure(Exception("Session not found"))
                authClient.logout(session.accessToken)
            }
        ) { response ->
            scope.launch {
                authHelper.deleteSession()
            }
            emit(
                RequestState.Success(
                    data = true
                )
            )
        }
    }
}
