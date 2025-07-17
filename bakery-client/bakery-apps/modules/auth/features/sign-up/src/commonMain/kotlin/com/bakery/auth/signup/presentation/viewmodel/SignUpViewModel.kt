package com.bakery.auth.signup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.bakery.auth.signup.data.SignUpRepository
import com.bakery.auth.signup.domain.model.SignUpForm
import com.bakery.auth.signup.domain.rules.SignUpValidation
import com.bakery.auth.signup.domain.rules.SignUpValidator
import com.bakery.auth.signup.presentation.events.SignUpEvents
import com.bakery.auth.signup.presentation.state.SignUpState
import com.bakery.core.state.RequestState
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.welcome
import com.bakery.types.common.Time.parseDateToString
import com.bakery.ui.navigation.AuthGraphRoute
import com.bakery.ui.navigation.HomeGraphRoute
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: SignUpRepository
) : ViewModel(), BaseViewModel {
    override val scope: CoroutineScope = viewModelScope

    private val formState: MutableStateFlow<SignUpForm> = MutableStateFlow(SignUpForm())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val formValidationState: StateFlow<SignUpValidation> = formState
        .map { it }
        .distinctUntilChanged()
        .debounce(300)
        .flatMapLatest { form ->
            SignUpValidator.validate(form)
        }
        .stateIn(
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
            // todo: fix
        )
    }
        .distinctUntilChanged()
        .onEach { state ->
            _state.update {
                state.copy(
                    submitEnabled = !state.submitLoading &&
                        if (state.signUpForm.hasErrors) {
                            state.formValidation.valid()
                        } else {
                            false
                        },
                    nextEnabled = when (state.pageIndex) {
                        0 -> {
                            SignUpValidator.validateFirstName(state.signUpForm.firstName, true) == null &&
                                SignUpValidator.validateLastName(state.signUpForm.lastName, true) == null &&
                                SignUpValidator.validateBirthDate(state.signUpForm.birthDate, true) == null
                        }
                        1 -> {
                            SignUpValidator.validateAddress(state.signUpForm.address, true) == null &&
                                SignUpValidator.validateEmail(state.signUpForm.email, true) == null &&
                                SignUpValidator.validatePhoneNumber(state.signUpForm.phoneNumber, true) == null
                        }
                        else -> {
                            false
                        }
                    }
                )
            }
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value,
        )

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.OnIndexChanged -> indexChanged(event.index)
            is SignUpEvents.OnFirstNameChanged -> firstNameChanged(event.value)
            is SignUpEvents.OnLastNameChanged -> lastNameChanged(event.value)
            is SignUpEvents.OnPhoneChanged -> phoneChanged(event.value)
            is SignUpEvents.OnBirthDateChanged -> birthDateChanged(event.value)
            is SignUpEvents.OnAddressChanged -> addressChanged(event.value)
            is SignUpEvents.OnEmailChanged -> emailChanged(event.value)
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

    private fun indexChanged(index: Int) {
        _state.update { state ->
            state.copy(
                pageIndex = index,
            )
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

    private fun phoneChanged(value: String) {
        formState.update { state ->
            state.copy(
                phoneNumber = value,
                showPhoneNumberError = true,
            )
        }
    }

    private fun birthDateChanged(value: Long) {
        formState.update { state ->
            state.copy(
                birthDate = value.parseDateToString(),
                showBirthDateError = true,
            )
        }
    }

    private fun addressChanged(value: String) {
        formState.update { state ->
            state.copy(
                address = value,
                showAddressError = true,
            )
        }
    }

    fun emailChanged(value: String) {
        formState.update { state ->
            state.copy(
                email = value,
                showEmailError = true,
            )
        }
    }

    private fun usernameChanged(value: String) {
        formState.update { state ->
            state.copy(
                username = value,
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
        // todo:
    }

    private fun onPrivacyPolicy() {
        // todo:
    }

    private fun submit() {
        viewModelScope.launch {
            repository.signUp(
                signUpForm = formState.value,
            ).collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        // todo: handle email and username duplicated errors and navigate
                        //       to the corresponding page of the form
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitError = result.error,
                                submitSuccess = false,
                            )
                        }

                        delay(5000)
                        _state.update { state ->
                            state.copy(
                                submitError = null,
                            )
                        }
                    }
                    is RequestState.Success -> {
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitError = null,
                                submitSuccess = true,
                            )
                        }

                        sendMessage(
                            message = Res.string.welcome,
                            description = state.value.signUpForm.firstName,
                        )

                        delay(1000)
                        navigateTo(
                            destination = HomeGraphRoute,
                            navOptions = navOptions {
                                popUpTo(AuthGraphRoute) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        )
                    }
                    else -> {
                        _state.update { state ->
                            state.copy(
                                submitLoading = true,
                                submitError = null,
                                submitSuccess = false,
                            )
                        }
                    }
                }
            }
        }
    }
}
