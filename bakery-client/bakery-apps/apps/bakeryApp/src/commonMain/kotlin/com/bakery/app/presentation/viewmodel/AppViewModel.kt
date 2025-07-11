package com.bakery.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.bakery.app.data.AppRepository
import com.bakery.ui.navigation.AuthGraphRoute
import com.bakery.ui.navigation.SplashRoute
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppViewModel(private val repository: AppRepository) :
    ViewModel(),
    BaseViewModel {
    override val scope: CoroutineScope = viewModelScope

    fun checkSession() {
        scope.launch {
            navigateTo(
                destination = AuthGraphRoute,
                navOptions = navOptions {
                    popUpTo(SplashRoute) {
                        inclusive = true
                    }
                    launchSingleTop = true
                },
            )
        }
    }

    fun endSession() {
        scope.launch {
            repository.endSession()
        }
    }
}
