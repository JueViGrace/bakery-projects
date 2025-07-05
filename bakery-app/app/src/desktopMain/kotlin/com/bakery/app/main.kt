package com.bakery.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bakery.app.di.appModule
import com.bakery.app.presentation.ui.screens.App
import com.bakery.ui.components.containers.AppContainer
import org.koin.core.context.startKoin

fun main() {
    application {
        startKoin {
            modules(appModule())
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Bakery and Deserts",
        ) {
            AppContainer {
                App()
            }
        }
    }
}
