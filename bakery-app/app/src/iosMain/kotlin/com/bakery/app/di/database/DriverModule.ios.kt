package com.bakery.app.di.database

import app.cash.sqldelight.async.coroutines.synchronous
import com.bakery.database.BakeryDB
import com.bakery.database.driver.DriverFactory
import com.bakery.database.driver.DriverFactoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
* Database driver dependency injection module for Ios target.
* */
actual fun driverModule(): Module = module {
    singleOf(::DriverFactoryImpl) bind DriverFactory::class

    single<BakeryDB> {
        val driver: DriverFactory = get()
        BakeryDB(driver.createDriver(BakeryDB.Schema.synchronous(), "bakery.db"))
    }
}
