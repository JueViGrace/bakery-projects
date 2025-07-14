package com.bakery.auth.signin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.bakery.auth.signin.data.SignInRepository
import com.bakery.auth.signin.domain.model.SignInForm
import com.bakery.auth.signin.domain.rules.SignInValidation
import com.bakery.auth.signin.domain.rules.SignInValidator
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.core.state.RequestState
import com.bakery.ui.model.SocialProvider
import com.bakery.ui.navigation.AuthGraphRoute
import com.bakery.ui.navigation.ForgotRoute
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class SignInViewModel(private val repository: SignInRepository) :
    ViewModel(),
    BaseViewModel {
    override val scope: CoroutineScope = viewModelScope

    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())

    private val formState: MutableStateFlow<SignInForm> = MutableStateFlow(SignInForm())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val formValidationState: StateFlow<SignInValidation> = formState
        .map { it }
        .distinctUntilChanged()
        .debounce(300)
        .flatMapLatest { form ->
            SignInValidator.validate(form)
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = SignInValidation(),
        )

    val state: StateFlow<SignInState> = combine(
        _state,
        formState,
        formValidationState,
    ) { state, formState, formValidationState ->
        state.copy(
            signInForm = formState,
            formValidation = formValidationState,
            submitEnabled = !state.submitLoading && (if (formState.hasErrors) formValidationState.valid() else false)
        )
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _state.value,
    )

    fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnUsernameChanged -> usernameChanged(event.email)
            is SignInEvents.OnForgot -> navigateToForgot(Json.encodeToString(event.action))
            is SignInEvents.OnPasswordChanged -> passwordChanged(event.password)
            is SignInEvents.OnSocialLogin -> handleSocialLogin(event.provider)
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
                    submitError = null,
                )
            }
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

    private fun handleSocialLogin(provider: SocialProvider) {
    }

    fun navigateToForgot(action: String) {
        navigateTo(
            destination = ForgotRoute(action),
            navOptions = navOptions {
                popUpTo(SignInRoute) {
                    inclusive = false
                }
                launchSingleTop = true
            },
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
            },
        )
    }

    private fun passwordChanged(value: String) {
        formState.update { state ->
            state.copy(
                password = value,
                showPasswordError = true,
            )
        }
    }

    private fun submit() {
        scope.launch {
            repository.login(formState.value).collect { result ->
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
                            },
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
