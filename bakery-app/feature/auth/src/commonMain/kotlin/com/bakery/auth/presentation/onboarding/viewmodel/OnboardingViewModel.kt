package com.bakery.auth.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope


class OnboardingViewModel : BaseViewModel, ViewModel() {
    override val scope: CoroutineScope = viewModelScope
}