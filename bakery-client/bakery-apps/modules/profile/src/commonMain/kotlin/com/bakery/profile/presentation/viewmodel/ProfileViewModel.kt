package com.bakery.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.profile.data.ProfileRepository
import com.bakery.profile.presentation.events.ProfileEvents
import com.bakery.profile.presentation.state.ProfileState
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
            // todo: log out in the server


        }
    }
}
