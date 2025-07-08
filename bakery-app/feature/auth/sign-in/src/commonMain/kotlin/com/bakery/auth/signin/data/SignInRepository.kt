package com.bakery.auth.signin.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.types.mappers.network.toDbSession
import com.bakery.auth.types.signin.LogInForm
import com.bakery.types.data.Repository
import com.bakery.types.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface SignInRepository : Repository {
    fun login(logInForm: LogInForm): Flow<RequestState<Boolean>>
}

class DefaultSignInRepository(
    private val client: AuthClient,
    private val helper: AuthHelper,
) : SignInRepository {
    override fun login(logInForm: LogInForm): Flow<RequestState<Boolean>> {
        return startNetworkRequest(
            call = {
                client.login(logInForm.toDto())
            }
        ) { value ->
            scope.launch {
                helper.createSession(value.toDbSession(active = true))
            }
            emit(RequestState.Success(true))
        }
    }
}
