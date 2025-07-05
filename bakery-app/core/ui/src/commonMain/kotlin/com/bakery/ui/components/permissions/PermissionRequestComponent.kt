package com.bakery.ui.components.permissions

import androidx.compose.runtime.Composable
import com.bakery.ui.model.Permissions

@Composable
expect fun PermissionRequestComponent(vararg permissions: Permissions, granted: () -> Unit)
