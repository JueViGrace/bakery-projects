package com.bakery.auth.forgot.di

import com.bakery.auth.forgot.data.DefaultForgotRepository
import com.bakery.auth.forgot.data.ForgotRepository
import com.bakery.auth.forgot.presentation.viewmodel.ForgotViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun forgotModule(): Module = module {
    singleOf(::DefaultForgotRepository) bind ForgotRepository::class

    viewModelOf(::ForgotViewModel)
}
