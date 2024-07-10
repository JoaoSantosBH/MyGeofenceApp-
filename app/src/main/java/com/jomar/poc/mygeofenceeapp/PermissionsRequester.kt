package com.jomar.poc.mygeofenceeapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

val geoFencePermissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    Manifest.permission.INTERNET,
    Manifest.permission.POST_NOTIFICATIONS,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

fun hasAllPermissions(context: Context, permissions: Array<String>) = permissions.all {
    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}


fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        // Use location because permissions are already granted
        Log.d(TAG, "checkAndRequestLocationPermissions: PERMISSION_GRANTED")
    } else {
        // Request permissions
        Log.d(TAG, "checkAndRequestLocationPermissions: PERMISSION_NOT_GRANTED")
        launcher.launch(permissions)
    }
}


