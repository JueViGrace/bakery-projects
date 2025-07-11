package com.bakery.ui.model

sealed interface Permissions {
    sealed interface LocationPermissions : Permissions {
        data object AccessCoarseLocation : LocationPermissions
        data object AccessFineLocation : LocationPermissions
    }
}
