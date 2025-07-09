package com.bakery.auth.signin.domain.rules

import com.bakery.auth.types.signin.LogInForm
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.password_empty
import com.bakery.resources.generated.resources.username_empty
import com.bakery.types.validation.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jetbrains.compose.resources.StringResource

internal object LoginValidator {
    fun validate(form: LogInForm): Flow<LoginValidation> = flow {
        var validation = LoginValidation()

        if (form.username.isEmpty()) {
            validation =
                validation.copy(
                    usernameError = Res.string.username_empty,
                )
        }

        if (form.password.isEmpty()) {
            validation =
                validation.copy(
                    passwordError = Res.string.password_empty,
                )
        }

        emit(validation)
    }.flowOn(Dispatchers.Default)
}

data class LoginValidation(val usernameError: StringResource? = null, val passwordError: StringResource? = null) : ValidationResult {
    override fun valid(): Boolean = usernameError == null && passwordError == null
}
