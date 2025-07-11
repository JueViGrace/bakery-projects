package com.bakery.app

import android.app.Application
import com.bakery.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BakeryApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize dependency injection framework
        startKoin {
            androidContext(this@BakeryApp)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(appModule())
        }
    }
}
