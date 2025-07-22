package com.bakery.auth.signin.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.signin.domain.model.SignInForm
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import migrations.Session as DBSession

interface SignInRepository : Repository {
    fun login(signInForm: SignInForm): Flow<RequestState<Boolean>>
}

class DefaultSignInRepository(private val client: AuthClient, private val helper: AuthHelper) : SignInRepository {
    override fun login(signInForm: SignInForm): Flow<RequestState<Boolean>> = startNetworkRequest(
        call = {
            client.logIn(signInForm.toDto())
        },
    ) { value ->
        val data = value.data
            ?: return@startNetworkRequest emit(RequestState.Error(value.message))
        scope.launch {
            helper.createSession(
                DBSession(
                    id = data.id,
                    accessToken = data.accessToken,
                    refreshToken = data.refreshToken,
                    active = true,
                )
            )
        }
        emit(RequestState.Success(true))
    }
}
