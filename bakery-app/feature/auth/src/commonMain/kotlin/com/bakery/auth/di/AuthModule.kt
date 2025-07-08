package com.bakery.auth.di

import com.bakery.auth.database.helper.AuthHelper
import com.bakery.auth.database.helper.DefaultAuthHelper
import com.bakery.auth.forgot.di.forgotModule
import com.bakery.auth.network.client.AuthClient
import com.bakery.auth.network.client.DefaultAuthClient
import com.bakery.auth.signin.di.signInModule
import com.bakery.auth.signup.di.signUpModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun authModule(): Module = module {
    singleOf(::DefaultAuthHelper) bind AuthHelper::class

    singleOf(::DefaultAuthClient) bind AuthClient::class

    includes(
        signInModule(),
        signUpModule(),
        forgotModule()
    )
}
