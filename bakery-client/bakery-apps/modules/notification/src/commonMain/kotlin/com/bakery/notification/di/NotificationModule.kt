package com.bakery.notification.di

import com.bakery.notification.data.DefaultNotificationRepository
import com.bakery.notification.data.NotificationRepository
import com.bakery.notification.presentation.viewmodel.NotificationViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun notificationModule(): Module = module {
    singleOf(::DefaultNotificationRepository) bind NotificationRepository::class

    viewModelOf(::NotificationViewModel)
}
