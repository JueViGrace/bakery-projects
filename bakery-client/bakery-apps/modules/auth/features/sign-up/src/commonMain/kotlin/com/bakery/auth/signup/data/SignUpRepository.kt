package com.bakery.auth.signup.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.core.data.Repository
import com.bakery.core.state.RequestState
import com.bakery.types.auth.signup.SignUpForm
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
        scope.launch {
            helper.createSession(
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
