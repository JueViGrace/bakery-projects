package com.bakery.auth.signin.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import com.bakery.types.auth.signin.LogInForm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import migrations.Session as DBSession

interface SignInRepository : Repository {
    fun login(logInForm: LogInForm): Flow<RequestState<Boolean>>
}

class DefaultSignInRepository(private val client: AuthClient, private val helper: AuthHelper) : SignInRepository {
    override fun login(logInForm: LogInForm): Flow<RequestState<Boolean>> = startNetworkRequest(
        call = {
            client.login(logInForm.toDto())
        },
    ) { value ->
        scope.launch {
            helper.createSession(
                DBSession(
                    id = value.id,
                    accessToken = value.accessToken,
                    refreshToken = value.refreshToken,
                    active = true,
                )
            )
        }
        emit(RequestState.Success(true))
    }
}
