package com.bakery.profile.di

import com.bakery.profile.data.DefaultProfileRepository
import com.bakery.profile.data.ProfileRepository
import com.bakery.profile.presentation.viewmodel.ProfileViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun profileModule(): Module = module {
    singleOf(::DefaultProfileRepository) bind ProfileRepository::class

    viewModelOf(::ProfileViewModel)
}
