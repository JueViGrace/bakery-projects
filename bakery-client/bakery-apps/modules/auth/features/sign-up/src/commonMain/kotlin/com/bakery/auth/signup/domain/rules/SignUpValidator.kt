package com.bakery.auth.signup.domain.rules

import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.email_empty
import com.bakery.resources.generated.resources.email_invalid_format
import com.bakery.resources.generated.resources.email_too_long
import com.bakery.resources.generated.resources.first_name_empty
import com.bakery.resources.generated.resources.last_name_empty
import com.bakery.resources.generated.resources.password_empty
import com.bakery.resources.generated.resources.phone_number_empty
import com.bakery.resources.generated.resources.privacy_policy_not_accepted
import com.bakery.resources.generated.resources.terms_and_conditions_not_accepted
import com.bakery.resources.generated.resources.username_empty
import com.bakery.types.auth.signup.SignUpForm
import com.bakery.types.validation.EmailValidationError
import com.bakery.types.validation.EmailValidationResult
import com.bakery.types.validation.EmailValidator
import com.bakery.types.validation.ValidationResult
import com.bakery.types.validation.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jetbrains.compose.resources.StringResource

internal object SignUpValidator {
    fun validate(form: SignUpForm): Flow<SignUpValidation> = flow {
        var validation = SignUpValidation()

        if (form.firstName.isEmpty()) {
            validation = validation.copy(
                firstNameError = Res.string.first_name_empty,
            )
        }

        if (form.lastName.isEmpty()) {
            validation = validation.copy(
                lastNameError = Res.string.last_name_empty,
            )
        }

        if (!form.email.isValidEmail()) {
            val valid = EmailValidator.validate(form.email) as EmailValidationResult.Invalid
            validation = validation.copy(
                emailError = when (valid.reason) {
                    EmailValidationError.EMPTY -> Res.string.email_empty
                    EmailValidationError.TOO_LONG -> Res.string.email_too_long
                    EmailValidationError.INVALID_FORMAT -> Res.string.email_invalid_format
                },
            )
        }

        if (form.username.isEmpty()) {
            validation = validation.copy(
                usernameError = Res.string.username_empty,
            )
        }

        if (form.password.isEmpty()) {
            validation = validation.copy(
                passwordError = Res.string.password_empty,
            )
        }

        // todo: validate phone number
        if (form.phoneNumber.isEmpty()) {
            validation = validation.copy(
                phoneNumberError = Res.string.phone_number_empty,
            )
        }

        if (!form.termsAndConditions) {
            validation = validation.copy(
                termsAndConditionsError = Res.string.terms_and_conditions_not_accepted,
            )
        }

        if (!form.privacyPolicy) {
            validation = validation.copy(
                privacyPolicyError = Res.string.privacy_policy_not_accepted,
            )
        }

        emit(validation)
    }.flowOn(Dispatchers.Default)
}

data class SignUpValidation(
    val firstNameError: StringResource? = null,
    val lastNameError: StringResource? = null,
    val usernameError: StringResource? = null,
    val aliasError: StringResource? = null,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val phoneNumberError: StringResource? = null,
    val termsAndConditionsError: StringResource? = null,
    val privacyPolicyError: StringResource? = null,
) : ValidationResult {
    override fun valid(): Boolean = firstNameError == null &&
        lastNameError == null &&
        usernameError == null &&
        aliasError == null &&
        emailError == null &&
        passwordError == null &&
        phoneNumberError == null &&
        termsAndConditionsError == null &&
        privacyPolicyError == null
}
