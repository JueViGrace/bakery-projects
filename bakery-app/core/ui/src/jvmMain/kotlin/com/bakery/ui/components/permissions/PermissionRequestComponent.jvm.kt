package com.bakery.ui.components.permissions

import androidx.compose.runtime.Composable
import com.bakery.ui.model.Permissions

@Composable
actual fun PermissionRequestComponent(vararg permissions: Permissions, granted: () -> Unit) {
    granted()
}
