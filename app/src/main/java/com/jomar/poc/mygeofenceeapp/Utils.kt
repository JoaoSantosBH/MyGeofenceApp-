package com.jomar.poc.mygeofenceeapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun openApplicationSettings(activity: Activity, packageName: String) {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also {
        activity.startActivity(it)
    }
}


