package com.bakery.auth.signin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.bakery.auth.signin.data.SignInRepository
import com.bakery.auth.signin.domain.rules.LoginValidation
import com.bakery.auth.signin.domain.rules.LoginValidator
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.types.signin.LogInForm
import com.bakery.types.state.RequestState
import com.bakery.ui.navigation.AuthGraphRoute
import com.bakery.ui.navigation.ForgotPasswordRoute
import com.bakery.ui.navigation.HomeGraphRoute
import com.bakery.ui.navigation.SignInRoute
import com.bakery.ui.navigation.SignUpRoute
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: SignInRepository,
) : BaseViewModel, ViewModel() {
    override val scope: CoroutineScope = viewModelScope

    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())

    private val formState: MutableStateFlow<LogInForm> = MutableStateFlow(LogInForm())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val formValidationState: StateFlow<LoginValidation> = formState
        .flatMapLatest { value ->
            LoginValidator.validate(value)
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = LoginValidation()
        )

    val state: StateFlow<SignInState> = combine(
        _state,
        formState,
        formValidationState,
    ) { state, formState, formValidationState ->
        state.copy(
            logInForm = formState,
            formValidation = formValidationState.copy(
                usernameError = if (formState.shouldShowUsernameError) formValidationState.usernameError else null,
                passwordError = if (formState.shouldShowPasswordError) formValidationState.passwordError else null,
            ),
            submitEnabled = (!state.submitLoading && formValidationState.valid()) &&
                (formState.username.isNotBlank() && formState.password.isNotBlank()),
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _state.value
    )

    fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnEmailChanged -> emailChanged(event.email)
            SignInEvents.OnForgotPassword -> navigateToForgotPassword()
            is SignInEvents.OnPasswordChanged -> passwordChanged(event.password)
            SignInEvents.OnSubmit -> submit()
            SignInEvents.ClearError -> clearError()
            SignInEvents.OnSignUp -> navigateToSignUp()
        }
    }

    private fun clearError() {
        scope.launch {
            delay(5000)
            _state.update { state ->
                state.copy(
                    submitError = null
                )
            }
        }
    }

    private fun emailChanged(value: String) {
        formState.update { state ->
            state.copy(
                username = value,
            )
        }
        scope.launch {
            delay(1000)
            formState.update { state ->
                state.copy(
                    shouldShowUsernameError = true
                )
            }
        }
    }

    fun navigateToForgotPassword() {
        navigateTo(
            destination = ForgotPasswordRoute,
            navOptions = navOptions {
                popUpTo(SignInRoute) {
                    inclusive = false
                }
                launchSingleTop = true
            }
        )
    }

    fun navigateToSignUp() {
        navigateTo(
            destination = SignUpRoute,
            navOptions = navOptions {
                popUpTo(SignInRoute) {
                    inclusive = false
                }
                launchSingleTop = true
            }
        )
    }

    private fun passwordChanged(value: String) {
        formState.update { state ->
            state.copy(
                password = value,
            )
        }
        scope.launch {
            delay(1000)
            formState.update { state ->
                state.copy(
                    shouldShowPasswordError = true
                )
            }
        }
    }

    private fun submit() {
        _state.update { state ->
            state.copy(
                submitLoading = true,
                submitError = null
            )
        }

        scope.launch {
            repository.login(formState.value).collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitError = result.error
                            )
                        }
                    }

                    is RequestState.Success -> {
                        _state.update { state ->
                            state.copy(
                                submitLoading = false,
                                submitError = null,
                            )
                        }

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
                            )
                        }
                    }
                }
            }
        }
    }
}
