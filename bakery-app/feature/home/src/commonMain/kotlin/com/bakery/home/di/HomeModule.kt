package com.bakery.home.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.rotau.presencia.category.di.categoryModule
import org.rotau.presencia.dashboard.di.dashboardModule
import org.rotau.presencia.home.data.DefaultHomeRepository
import org.rotau.presencia.home.data.HomeRepository
import org.rotau.presencia.home.presentation.viewmodel.HomeViewModel
import org.rotau.presencia.hr.di.hrModule
import org.rotau.presencia.portal.di.portalModule
import org.rotau.presencia.presence.di.presenceModule

fun homeModule(): Module = module {
    includes(
        categoryModule(),
        dashboardModule(),
        hrModule(),
        presenceModule(),
        portalModule()
    )

    singleOf(::DefaultHomeRepository) bind HomeRepository::class

    viewModelOf(::HomeViewModel)
}
