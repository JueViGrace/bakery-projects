package com.bakery.auth.data

import com.bakery.auth.domain.model.LogInForm
import com.bakery.auth.domain.model.PasswordReset
import com.bakery.auth.domain.model.SignUpForm
import com.bakery.types.data.Repository
import com.bakery.types.state.RequestState
import kotlinx.coroutines.flow.Flow

interface AuthRepository : Repository {
    fun confirmPasswordReset(code: String): Flow<RequestState<Boolean>>
    fun login(logInForm: LogInForm): Flow<RequestState<Boolean>>
    fun logout(): Flow<RequestState<Boolean>>
    fun refresh(): Flow<RequestState<Boolean>>
    fun requestPasswordReset(username: String): Flow<RequestState<Boolean>>
    fun resetPassword(reset: PasswordReset): Flow<RequestState<Boolean>>
    fun signUp(signUpForm: SignUpForm): Flow<RequestState<Boolean>>
}
