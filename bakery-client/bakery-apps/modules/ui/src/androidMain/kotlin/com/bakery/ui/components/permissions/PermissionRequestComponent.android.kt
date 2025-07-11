package com.bakery.ui.components.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.bakery.ui.model.Permissions
import com.bakery.util.Logs

@Composable
actual fun PermissionRequestComponent(vararg permissions: Permissions, granted: () -> Unit) {
    val permissionList: List<String> = permissions.map {
        when (it) {
            is Permissions.LocationPermissions.AccessCoarseLocation -> Manifest.permission.ACCESS_COARSE_LOCATION
            is Permissions.LocationPermissions.AccessFineLocation -> Manifest.permission.ACCESS_FINE_LOCATION
        }
    }.filter { it.isNotEmpty() }
    val context: Context = LocalContext.current

    val launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { results ->
            if (!results.values.all { it }) {
                Logs.error("RequestPermissionComponent", "Permission denied")
            } else {
                granted()
            }
        }

    val hasPermissions: Boolean by remember {
        derivedStateOf {
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ).all { perm ->
                ContextCompat.checkSelfPermission(context, perm) ==
                    PackageManager.PERMISSION_GRANTED
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermissions) {
            launcher.launch(permissionList.toTypedArray())
        }
    }
}
