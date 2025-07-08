package com.bakery.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.bakery.home.data.HomeRepository
import com.bakery.home.presentation.events.HomeEvents
import com.bakery.home.presentation.state.HomeState
import com.bakery.ui.layout.bars.NavBarState
import com.bakery.ui.layout.bars.NavBars
import com.bakery.ui.navigation.HomeTabGraphRoute
import com.bakery.ui.navigation.tab.Tab
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : BaseViewModel, ViewModel() {
    override val scope: CoroutineScope = viewModelScope

    private val bottomBarState: MutableStateFlow<NavBarState> = MutableStateFlow(NavBarState())

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = combine(
        _state,
        bottomBarState
    ) { state, bottomState ->
        state.copy(
            bottomBarState = bottomState,
        )
    }.stateIn(
        scope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.OnTabSelected -> tabSelected(event.index, event.bar)
            HomeEvents.ToggleMore -> toggleMoreCategories()
            HomeEvents.OnAccount -> showAccount()
            HomeEvents.OnNotifications -> showNotifications()
            HomeEvents.ToggleSearch -> toggleSearch()
        }
    }

    private fun tabSelected(index: Int, bar: NavBars) {
        when (bar) {
            NavBars.BottomBar -> {
                updateBarState(
                    barState = bottomBarState,
                    index = index,
                    onUpdated = { destination ->
                        scope.launch {
                            tabNavigator.navigate(
                                destination = destination.route,
                                navOptions = navOptions {
                                    popUpTo(HomeTabGraphRoute) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            )
                        }
                    }
                )
            }
            NavBars.SideBar -> {}
            NavBars.TopBar -> {}
        }
    }

    private fun updateBarState(
        barState: MutableStateFlow<NavBarState>,
        index: Int,
        onUpdated: (destination: Tab) -> Unit = {}
    ) {
        val tab: Tab = barState.value.tabs[index]
        if (barState.value.selectedTab == tab) return
        barState.update { state ->
            state.copy(
                selectedTab = tab,
                selectedTabIndex = index,
            )
        }
        onUpdated(tab)
    }

    private fun showAccount() {
        _state.update { state ->
            state.copy(
                showAccount = !state.showAccount
            )
        }
    }

    private fun showNotifications() {
        _state.update { state ->
            state.copy(
                showNotifications = !state.showNotifications
            )
        }
    }

    private fun toggleMoreCategories() {
        _state.update { state ->
            state.copy(
                showMoreCategories = !state.showMoreCategories
            )
        }
    }

    private fun toggleSearch() {
        _state.update { state ->
            state.copy(
                showSearch = !state.showSearch
            )
        }
    }
}
