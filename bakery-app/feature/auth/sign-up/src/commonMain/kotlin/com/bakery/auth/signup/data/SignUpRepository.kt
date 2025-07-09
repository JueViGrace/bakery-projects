package com.bakery.auth.signup.data

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.types.mappers.network.toDbSession
import com.bakery.auth.types.signup.SignUpForm
import com.bakery.types.data.Repository
import com.bakery.types.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
            helper.createSession(value.toDbSession(active = true))
        }
        emit(RequestState.Success(true))
    }
}
