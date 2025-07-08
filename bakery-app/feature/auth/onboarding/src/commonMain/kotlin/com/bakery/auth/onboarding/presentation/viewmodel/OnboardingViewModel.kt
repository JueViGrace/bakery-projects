package com.bakery.auth.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope

class OnboardingViewModel :
    ViewModel(),
    BaseViewModel {
    override val scope: CoroutineScope = viewModelScope
}

