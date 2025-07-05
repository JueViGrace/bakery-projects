package com.bakery.auth.di

import com.bakery.auth.data.AuthRepository
import com.bakery.auth.data.DefaultAuthRepository
import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.database.helper.DefaultAuthHelper
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.network.client.DefaultAuthClient
import com.bakery.auth.presentation.onboarding.viewmodel.OnboardingViewModel
import com.bakery.auth.presentation.signin.viewmodel.SignInViewModel
import com.bakery.auth.presentation.signup.viewmodel.SignUpViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun authModule(): Module = module {
    singleOf(::DefaultAuthHelper) bind AuthHelper::class

    singleOf(::DefaultAuthClient) bind AuthClient::class

    singleOf(::DefaultAuthRepository) bind AuthRepository::class

    viewModelOf(::OnboardingViewModel)

    viewModelOf(::SignInViewModel)

    viewModelOf(::SignUpViewModel)
}
