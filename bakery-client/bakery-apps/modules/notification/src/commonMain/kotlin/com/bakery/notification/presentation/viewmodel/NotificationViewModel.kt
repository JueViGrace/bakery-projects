package com.bakery.notification.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.notification.data.NotificationRepository
import com.bakery.notification.presentation.events.NotificationEvents
import com.bakery.notification.presentation.state.NotificationState
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationViewModel(
    private val repository: NotificationRepository
) : BaseViewModel, ViewModel() {
    override val scope: CoroutineScope = viewModelScope

    private val _state: MutableStateFlow<NotificationState> = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    fun onEvent(event: NotificationEvents) {
        when (event) {
            is NotificationEvents.OnDeleteNotification -> deleteNotification(event.id)
        }
    }

    private fun deleteNotification(id: String) {
    }
}
