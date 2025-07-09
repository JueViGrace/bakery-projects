package com.bakery.auth.signup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.auth.signup.data.SignUpRepository
import com.bakery.auth.signup.domain.rules.SignUpValidation
import com.bakery.auth.signup.domain.rules.SignUpValidator
import com.bakery.auth.signup.presentation.state.SignUpState
import com.bakery.auth.types.signup.SignUpForm
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class SignUpViewModel(private val repository: SignUpRepository) :
    ViewModel(),
    BaseViewModel {
    override val scope: CoroutineScope = viewModelScope

    private val formState: MutableStateFlow<SignUpForm> = MutableStateFlow(SignUpForm())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val formValidationState: StateFlow<SignUpValidation> = formState
        .debounce(1000)
        .distinctUntilChanged()
        .onEach { delay(100) }
        .flatMapLatest { value ->
            SignUpValidator.validate(value)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
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
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _state.value,
    )
}
