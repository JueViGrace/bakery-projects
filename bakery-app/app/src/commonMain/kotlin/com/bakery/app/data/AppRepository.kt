package com.bakery.app.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.types.mappers.database.toSession
import com.bakery.types.auth.Session
import com.bakery.types.data.Repository
import com.bakery.types.state.RequestState
import kotlinx.coroutines.flow.Flow

interface AppRepository : Repository {
    val session: Flow<RequestState<Session>>

    suspend fun endSession()
}

class DefaultAppRepository(
    private val authHelper: AuthHelper,
) : AppRepository {
    override val session: Flow<RequestState<Session>> = startFlow {
        val session: Session = authHelper.getSession()?.toSession()
            ?: return@startFlow emit(
                RequestState.Error(
                    error = "Session not found"
                )
            )

        emit(
            RequestState.Success(
                data = session
            )
        )
    }

    override suspend fun endSession() {
        authHelper.deleteSession()
    }
}
