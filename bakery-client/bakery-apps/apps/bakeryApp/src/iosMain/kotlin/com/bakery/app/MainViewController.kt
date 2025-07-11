package com.bakery.app

import androidx.compose.ui.window.ComposeUIViewController
import com.bakery.app.di.appModule
import com.bakery.app.presentation.ui.screens.App
import com.bakery.ui.components.containers.AppContainer
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(appModule())
    }

    AppContainer {
        App()
    }
}
