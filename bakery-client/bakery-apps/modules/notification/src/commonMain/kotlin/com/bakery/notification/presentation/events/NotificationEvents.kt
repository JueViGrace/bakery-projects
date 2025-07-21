package com.bakery.notification.presentation.events

sealed interface NotificationEvents {
    data class OnDeleteNotification(val id: String) : NotificationEvents
}
