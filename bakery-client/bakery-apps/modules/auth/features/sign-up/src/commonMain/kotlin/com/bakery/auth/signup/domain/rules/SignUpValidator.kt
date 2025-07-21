package com.bakery.auth.signup.domain.rules

import com.bakery.auth.signup.domain.model.SignUpForm
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.birth_date_empty
import com.bakery.resources.generated.resources.email_empty
import com.bakery.resources.generated.resources.email_invalid_format
import com.bakery.resources.generated.resources.email_too_long
import com.bakery.resources.generated.resources.first_name_empty
import com.bakery.resources.generated.resources.last_name_empty
import com.bakery.resources.generated.resources.password_empty
import com.bakery.resources.generated.resources.password_mismatch
import com.bakery.resources.generated.resources.phone_number_empty
import com.bakery.resources.generated.resources.privacy_policy_not_accepted
import com.bakery.resources.generated.resources.terms_and_conditions_not_accepted
import com.bakery.resources.generated.resources.username_with_whitespaces
import com.bakery.types.validation.EmailValidationError
import com.bakery.types.validation.EmailValidationResult
import com.bakery.types.validation.EmailValidator
import com.bakery.types.validation.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jetbrains.compose.resources.StringResource

internal object SignUpValidator {
    fun validateFirstName(firstName: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            firstName.trim().isEmpty() -> Res.string.first_name_empty
            else -> null
        }
    }

    fun validateLastName(lastName: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            lastName.trim().isEmpty() -> Res.string.last_name_empty
            else -> null
        }
    }

    // todo: validate number
    fun validatePhoneNumber(phoneNumber: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            phoneNumber.isEmpty() -> Res.string.phone_number_empty
            else -> null
        }
    }

    fun validateBirthDate(birthDate: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            birthDate.isEmpty() -> Res.string.birth_date_empty
            else -> null
        }
    }

    fun validateEmail(email: String, showError: Boolean): StringResource? {
        val valid: EmailValidationResult = EmailValidator.validate(email)
        return when {
            !showError -> null
            email.isEmpty() -> Res.string.email_empty
            valid is EmailValidationResult.Invalid -> {
                when (valid.reason) {
                    EmailValidationError.EMPTY -> Res.string.email_empty
                    EmailValidationError.TOO_LONG -> Res.string.email_too_long
                    EmailValidationError.INVALID_FORMAT -> Res.string.email_invalid_format
                }
            }
            else -> null
        }
    }

    fun validateUsername(username: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            username.any { it.isWhitespace() } -> Res.string.username_with_whitespaces
            else -> null
        }
    }

    private fun validatePassword(password: String, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            password.trim().isEmpty() -> Res.string.password_empty
            else -> null
        }
    }

    private fun validateConfirmPassword(
        password: String,
        confirmPassword: String,
        showError: Boolean,
    ): StringResource? {
        return when {
            !showError -> null
            confirmPassword.trim().isEmpty() -> Res.string.password_empty
            password != confirmPassword -> Res.string.password_mismatch
            else -> null
        }
    }

    private fun validateTermsAndConditions(termsAccepted: Boolean, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            !termsAccepted -> Res.string.terms_and_conditions_not_accepted
            else -> null
        }
    }

    private fun validatePrivacyPolicy(privacyPolicyAccepted: Boolean, showError: Boolean): StringResource? {
        return when {
            !showError -> null
            !privacyPolicyAccepted -> Res.string.privacy_policy_not_accepted
            else -> null
        }
    }

    fun validate(form: SignUpForm): Flow<SignUpValidation> = flow {
        emit(
            SignUpValidation(
                firstNameError = validateFirstName(form.firstName, form.showFirstNameError),
                lastNameError = validateLastName(form.lastName, form.showLastNameError),
                phoneNumberError = validatePhoneNumber(form.phoneNumber, form.showPhoneNumberError),
                birthDateError = validateBirthDate(form.birthDate, form.showBirthDateError),
                emailError = validateEmail(form.email, form.showEmailError),
                usernameError = validateUsername(form.username, form.showUsernameError),
                passwordError = validatePassword(form.password, form.showPasswordError),
                confirmPasswordError = validateConfirmPassword(
                    form.password,
                    form.confirmPassword,
                    form.showConfirmPasswordError,
                ),
                termsAndConditionsError = validateTermsAndConditions(
                    form.termsAndConditions,
                    form.showTermsAndConditionsError
                ),
                privacyPolicyError = validatePrivacyPolicy(form.privacyPolicy, form.showPrivacyPolicyError),
            )
        )
    }.flowOn(Dispatchers.Default)
}

data class SignUpValidation(
    val firstNameError: StringResource? = null,
    val lastNameError: StringResource? = null,
    val phoneNumberError: StringResource? = null,
    val birthDateError: StringResource? = null,
    val emailError: StringResource? = null,
    val usernameError: StringResource? = null,
    val passwordError: StringResource? = null,
    val confirmPasswordError: StringResource? = null,
    val termsAndConditionsError: StringResource? = null,
    val privacyPolicyError: StringResource? = null,
) : ValidationResult {
    override fun valid(): Boolean = firstNameError == null &&
        lastNameError == null &&
        birthDateError == null &&
        usernameError == null &&
        emailError == null &&
        passwordError == null &&
        confirmPasswordError == null &&
        phoneNumberError == null &&
        termsAndConditionsError == null &&
        privacyPolicyError == null
}
