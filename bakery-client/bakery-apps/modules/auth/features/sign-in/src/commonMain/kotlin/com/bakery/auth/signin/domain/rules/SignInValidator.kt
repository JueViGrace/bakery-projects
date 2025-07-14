package com.bakery.auth.signin.domain.rules

import com.bakery.auth.signin.domain.model.SignInForm
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.password_empty
import com.bakery.resources.generated.resources.username_empty
import com.bakery.types.validation.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jetbrains.compose.resources.StringResource

internal object SignInValidator {
    private fun validateUsername(username: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            username.isEmpty() -> Res.string.username_empty
            else -> null
        }
    }

    private fun validatePassword(password: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            password.isEmpty() -> Res.string.password_empty
            else -> null
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun validate(form: SignInForm): Flow<SignInValidation> = flow {
        emit(
            SignInValidation(
                usernameError = validateUsername(form.username, form.showUsernameError),
                passwordError = validatePassword(form.password, form.showPasswordError)
            )
        )
    }
        .flowOn(Dispatchers.Default)
}

data class SignInValidation(
    val usernameError: StringResource? = null,
    val passwordError: StringResource? = null,
) : ValidationResult {
    override fun valid(): Boolean = usernameError == null && passwordError == null
}
