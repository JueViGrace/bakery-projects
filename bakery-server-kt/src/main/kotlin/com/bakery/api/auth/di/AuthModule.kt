package com.bakery.api.auth.di

import com.bakery.api.auth.service.AuthService
import com.bakery.api.auth.service.JwtService
import com.bakery.api.auth.repository.RefreshTokenRepository
import com.bakery.api.auth.repository.RefreshTokenRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::RefreshTokenRepositoryImpl) bind RefreshTokenRepository::class

    singleOf(::JwtService)

    singleOf(::AuthService)
}
