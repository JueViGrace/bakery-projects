package com.bakery.auth.forgot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.auth.forgot.data.ForgotRepository
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope

class ForgotViewModel(private val repository: ForgotRepository) :
    ViewModel(),
    BaseViewModel {
    override val scope: CoroutineScope = viewModelScope
}
