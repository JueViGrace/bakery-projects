package org.rotau.presencia.home.presentation.viewmodel

import androidx.navigation.navOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rotau.home.presentation.viewmodel.SharedHomeViewModel
import org.rotau.presencia.home.data.HomeRepository
import com.bakery.home.presentation.events.HomeEvents
import com.bakery.home.presentation.state.HomeState
import org.rotau.ui.layout.bars.NavBarState
import org.rotau.ui.layout.bars.NavBars
import org.rotau.ui.navigation.HomeTabGraphRoute
import org.rotau.ui.navigation.tab.PresenceBottomTabs
import org.rotau.ui.navigation.tab.PresenceSecondaryTabs
import org.rotau.ui.navigation.tab.Tab

class HomeViewModel(
    private val repository: HomeRepository
) : SharedHomeViewModel() {
    private val topBarState: MutableStateFlow<NavBarState> = MutableStateFlow(NavBarState())

    private val bottomBarState: MutableStateFlow<NavBarState> = MutableStateFlow(NavBarState())

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = combine(
        _state,
        topBarState,
        bottomBarState
    ) { state, topState, bottomState ->
        state.copy(
            topBarState = topState,
            bottomBarState = bottomState,
        )
    }.stateIn(
        scope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )

    init {
        initTabs()
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.OnTabSelected -> tabSelected(event.index, event.bar)
            HomeEvents.HideTopBar -> hideTopBar()
            HomeEvents.ToggleMore -> toggleMoreCategories()
            HomeEvents.ShowTopBar -> showTopBar()
            HomeEvents.OnAccount -> showAccount()
            HomeEvents.OnNotifications -> showNotifications()
            HomeEvents.ToggleSearch -> toggleSearch()
        }
    }

    private fun initTabs() {
        val topTabs: List<Tab> = _state.value.topTabs
        topBarState.update { state ->
            val update: NavBarState = state.copy(
                tabs = topTabs,
                selectedTab = HomeState.defaultTopTabs[state.selectedTabIndex],
                showBar = topTabs.isNotEmpty(),
            )
            bottomBarState.update { bottomState ->
                val bottomTabs: List<Tab> = if (update.selectedTab is PresenceSecondaryTabs.Presence) {
                    _state.value.presenceTabs
                } else {
                    _state.value.commsTabs
                }
                bottomState.copy(
                    tabs = bottomTabs,
                    selectedTab = HomeState.defaultPresenceBottomTabs[bottomState.selectedTabIndex],
                    showBar = bottomTabs.isNotEmpty(),
                )
            }

            update
        }
    }

    private fun tabSelected(index: Int, bar: NavBars) {
        when (bar) {
            NavBars.BottomBar -> {
                updateBarState(
                    barState = bottomBarState,
                    index = index,
                    onUpdated = { destination ->
                        if (destination is PresenceBottomTabs.MoreTab) {
                            // todo: implement More tab
                            return@updateBarState
                        }

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
            NavBars.TopBar -> {
                updateBarState(
                    barState = topBarState,
                    index = index,
                    onUpdated = { destination ->
                        bottomBarState.update { state ->
                            val tabs: List<Tab> = if (destination is PresenceSecondaryTabs.Presence) {
                                _state.value.presenceTabs
                            } else {
                                _state.value.commsTabs
                            }
                            state.copy(
                                tabs = tabs,
                                showBar = tabs.isNotEmpty(),
                            )
                        }
                    },
                )
            }
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

    private fun hideTopBar() {
        topBarState.update { state ->
            state.copy(
                showBar = false,
            )
        }
        _state.update { state ->
            state.copy(
                swipeEnable = false
            )
        }
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

    private fun showTopBar() {
        scope.launch {
            delay(300)
            topBarState.update { state ->
                state.copy(
                    showBar = true
                )
            }
            _state.update { state ->
                state.copy(
                    swipeEnable = true
                )
            }
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
