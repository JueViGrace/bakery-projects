package com.bakery.auth.signin.di

import com.bakery.auth.signin.data.DefaultSignInRepository
import com.bakery.auth.signin.data.SignInRepository
import com.bakery.auth.signin.presentation.viewmodel.SignInViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun signInModule(): Module = module {
    singleOf(::DefaultSignInRepository) bind SignInRepository::class

    viewModelOf(::SignInViewModel)
}
