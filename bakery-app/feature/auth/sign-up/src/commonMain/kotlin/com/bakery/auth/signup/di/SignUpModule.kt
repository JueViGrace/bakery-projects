package com.bakery.auth.signup.di

import com.bakery.auth.signup.data.DefaultSignUpRepository
import com.bakery.auth.signup.data.SignUpRepository
import com.bakery.auth.signup.presentation.viewmodel.SignUpViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun signUpModule(): Module = module {
    singleOf(::DefaultSignUpRepository) bind SignUpRepository::class

    viewModelOf(::SignUpViewModel)
}
