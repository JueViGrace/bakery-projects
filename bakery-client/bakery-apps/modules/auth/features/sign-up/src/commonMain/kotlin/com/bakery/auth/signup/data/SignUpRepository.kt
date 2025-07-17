package com.bakery.auth.signup.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.signup.domain.model.SignUpForm
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import migrations.Session as DBSession

interface SignUpRepository : Repository {
    fun signUp(signUpForm: SignUpForm): Flow<RequestState<Boolean>>
}

class DefaultSignUpRepository(private val client: AuthClient, private val helper: AuthHelper) : SignUpRepository {
    override fun signUp(signUpForm: SignUpForm): Flow<RequestState<Boolean>> = startNetworkRequest(
        call = {
            client.signUp(signUpForm.toDto())
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
                    active = true
                )
            )
        }
        emit(RequestState.Success(true))
    }
}
