package com.bakery.app.di.database

import com.bakery.database.helper.DbHelper
import com.bakery.database.helper.DbHelperImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun databaseModule(): Module = module {
    includes(driverModule())

    singleOf(::DbHelperImpl) bind DbHelper::class
}
