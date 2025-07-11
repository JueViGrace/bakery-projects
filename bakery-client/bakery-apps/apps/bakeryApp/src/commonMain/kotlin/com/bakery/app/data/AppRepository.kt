package com.bakery.app.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import kotlinx.coroutines.flow.Flow

interface AppRepository : Repository {
    val session: Flow<RequestState<Boolean>>

    suspend fun endSession()
}

class DefaultAppRepository(private val authHelper: AuthHelper) : AppRepository {
    override val session: Flow<RequestState<Boolean>> = startFlow {
        val session = authHelper.getSession()
        if (session == null) {
            return@startFlow emit(
                RequestState.Error(
                    error = "Session not found",
                ),
            )
        }

        emit(RequestState.Success(true))
    }

    override suspend fun endSession() {
        authHelper.deleteSession()
    }
}
