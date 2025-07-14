package com.bakery.auth.signup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.auth.signup.data.SignUpRepository
import com.bakery.auth.signup.domain.model.SignUpForm
import com.bakery.auth.signup.domain.rules.SignUpValidation
import com.bakery.auth.signup.domain.rules.SignUpValidator
import com.bakery.auth.signup.presentation.events.SignUpEvents
import com.bakery.auth.signup.presentation.state.SignUpState
import com.bakery.core.state.RequestState
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: SignUpRepository) :
    ViewModel(),
    BaseViewModel {
    override val scope: CoroutineScope = viewModelScope

    private val formState: MutableStateFlow<SignUpForm> = MutableStateFlow(SignUpForm())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val formValidationState: StateFlow<SignUpValidation> = formState
        .map { it }
        .distinctUntilChanged()
        .debounce(300)
        .flatMapLatest { form ->
            SignUpValidator.validate(form)
        }.stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = SignUpValidation(),
        )

    private val _state: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = combine(
        _state,
        formState,
        formValidationState,
    ) { state, formState, formValidationState ->
        state.copy(
            signUpForm = formState,
            formValidation = formValidationState,
            submitEnabled = !state.submitLoading && (if (formState.hasErrors) formValidationState.valid() else false)
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _state.value,
    )

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.OnFirstNameChanged -> firstNameChanged(event.value)
            is SignUpEvents.OnLastNameChanged -> lastNameChanged(event.value)
            is SignUpEvents.OnAliasChanged -> aliasChanged(event.value)
            is SignUpEvents.OnEmailChanged -> emailChanged(event.value)
            is SignUpEvents.OnPhoneChanged -> phoneChanged(event.value)
            is SignUpEvents.OnUsernameChanged -> usernameChanged(event.value)
            is SignUpEvents.OnPasswordChanged -> passwordChanged(event.value)
            is SignUpEvents.OnConfirmPasswordChanged -> confirmPasswordChanged(event.value)
            SignUpEvents.ToggleTerms -> toggleTerms()
            SignUpEvents.TogglePrivacyPolicy -> togglePrivacyPolicy()
            SignUpEvents.OnSubmit -> submit()
            SignUpEvents.OnPrivacyPolicy -> onTerms()
            SignUpEvents.OnTerms -> onPrivacyPolicy()
        }
    }

    private fun firstNameChanged(value: String) {
        formState.update { state ->
            state.copy(
                firstName = value,
                showFirstNameError = true,
            )
        }
    }

    private fun lastNameChanged(value: String) {
        formState.update { state ->
            state.copy(
                lastName = value,
                showLastNameError = true,
            )
        }
    }

    private fun aliasChanged(value: String) {
        formState.update { state ->
            state.copy(
                alias = value,
                showAliasError = true,
            )
        }
    }

    private fun emailChanged(value: String) {
        formState.update { state ->
            state.copy(
                email = value,
                showEmailError = true,
            )
        }
    }

    private fun phoneChanged(value: String) {
        formState.update { state ->
            state.copy(
                phoneNumber = value,
                showPhoneNumberError = true,
            )
        }
    }

    private fun usernameChanged(value: String) {
        formState.update { state ->
            state.copy(
                username = value,
                showUsernameError = true,
            )
        }
    }

    private fun passwordChanged(value: String) {
        formState.update { state ->
            state.copy(
                password = value,
                showPasswordError = true,
            )
        }
    }

    private fun confirmPasswordChanged(value: String) {
        formState.update { state ->
            state.copy(
                confirmPassword = value,
                showConfirmPasswordError = true,
            )
        }
    }

    private fun toggleTerms() {
        formState.update { state ->
            state.copy(
                termsAndConditions = !state.termsAndConditions,
                showTermsAndConditionsError = true,
            )
        }
    }

    private fun togglePrivacyPolicy() {
        formState.update { state ->
            state.copy(
                privacyPolicy = !state.privacyPolicy,
                showPrivacyPolicyError = true,
            )
        }
    }

    private fun onTerms() {
    }

    private fun onPrivacyPolicy() {
    }

    private fun submit() {
        viewModelScope.launch {
            repository.signUp(
                signUpForm = formState.value,
            ).collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitError = result.error,
                            )
                        }
                    }
                    is RequestState.Success -> {
                    }
                    else -> {
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitError = null,
                            )
                        }
                    }
                }
            }
        }
    }
}
