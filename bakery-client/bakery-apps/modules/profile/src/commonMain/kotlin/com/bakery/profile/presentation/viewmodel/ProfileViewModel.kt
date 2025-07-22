package com.bakery.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.bakery.core.state.RequestState
import com.bakery.profile.data.ProfileRepository
import com.bakery.profile.presentation.events.ProfileEvents
import com.bakery.profile.presentation.state.ProfileState
import com.bakery.ui.navigation.AuthGraphRoute
import com.bakery.ui.navigation.HomeGraphRoute
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : BaseViewModel, ViewModel() {
    override val scope: CoroutineScope = viewModelScope

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun onEvent(event: ProfileEvents) {
        when (event) {
            ProfileEvents.OnLogOut -> logOut()
        }
    }

    private fun logOut() {
        scope.launch {
            repository.logOut().collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        // todo: error
                        navigateTo(
                            destination = AuthGraphRoute,
                            navOptions = navOptions {
                                popUpTo(HomeGraphRoute) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        )
                    }
                    is RequestState.Success -> {
                        navigateTo(
                            destination = AuthGraphRoute,
                            navOptions = navOptions {
                                popUpTo(HomeGraphRoute) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        )
                    }
                    else -> {
                        // todo: loading
                    }
                }
            }
        }
    }
}
