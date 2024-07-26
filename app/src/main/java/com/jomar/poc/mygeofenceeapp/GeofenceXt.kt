package com.jomar.poc.mygeofenceeapp

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.jomar.poc.mygeofenceeapp.MainActivity.Companion.ACTION_GEOFENCE_EVENT
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel.Companion.ALL_GEOFENCES


lateinit var geofencingClient: GeofencingClient
lateinit var myGeofencePendingIntent: PendingIntent
val geofenceList = mutableListOf<Geofence>()

fun geofencingClient(context: Context) = LocationServices.getGeofencingClient(context)

fun decideCurrentPermissionStatus(
    locationPermissionsGranted: Boolean,
    shouldShowPermissionRationale: Boolean
): String {
    return if (locationPermissionsGranted) "Granted"
    else if (shouldShowPermissionRationale) "Rejected"
    else "Denied"
}


fun populateGeoFanceList() {
        Log.d(GEO_TAG, "populateGeoFanceList")
    for (i in 0..ALL_GEOFENCES.size -1 ){
        geofenceList.add(
            Geofence.Builder()
                .setRequestId(ALL_GEOFENCES[i].id)
                .setCircularRegion(
                    ALL_GEOFENCES[i].latitude,
                    ALL_GEOFENCES[i].longitude,
                    ALL_GEOFENCES[i].radius
                )
                .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
        )
    }
}

fun addMyActualCustomLocation(customGeofence: GeofenceModel) {
    geofenceList.clear()
    Log.d(GEO_TAG, "addMyActualCustomLocation")
        geofenceList.add(
            Geofence.Builder()
                .setRequestId(customGeofence.id)
                .setCircularRegion(
                    customGeofence.latitude,
                    customGeofence.longitude,
                    customGeofence.radius
                )
                .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
        )
}

@RequiresApi(Build.VERSION_CODES.Q)
fun registerGeofences(applicationContext: Context) {
    Log.d(GEO_TAG, "registerGeofences")
    if (ActivityCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.d(GEO_TAG, "registerGeofences: PERMISSION_DENIED")
        return
    }

    val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(applicationContext.applicationContext, GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        val pendingFlags = if (Build.VERSION.SDK_INT >= 23) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        PendingIntent.getBroadcast(applicationContext, 0, intent, pendingFlags)
    }
    myGeofencePendingIntent = geofencePendingIntent
    geofencingClient.addGeofences(getGeofencingRequest(), myGeofencePendingIntent).run {
        addOnSuccessListener {
            Log.d(GEO_TAG, "Geofences added")
            Log.d(GEO_TAG, "Geofences size list ${geofenceList.size}")
            Log.d(GEO_TAG, "Geofences size list $geofenceList")
        }
        addOnFailureListener {
            Log.d(GEO_TAG, "Geofences failed")
        }
    }

}


fun stopGeoFences() {
    geofencingClient.removeGeofences(myGeofencePendingIntent).run {
        addOnSuccessListener {
            Log.d(GEO_TAG, "Geofences removed")
        }
        addOnFailureListener {
            Log.d(GEO_TAG, "Geofences failed")
        }
    }
}

fun getGeofencingRequest(): GeofencingRequest {
    return GeofencingRequest.Builder().apply {
        setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        addGeofences(geofenceList)
    }.build()
}

fun checkDeviceLocationSettingsAndStartGeofence(context: Context, resolve: Boolean = true) {
    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_LOW_POWER
    }
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

    val settingsClient = LocationServices.getSettingsClient(context.applicationContext)
    val locationSettingsResponseTask =
        settingsClient.checkLocationSettings(builder.build())

    locationSettingsResponseTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException && resolve) {
            try {
                exception.startResolutionForResult(
                    context.applicationContext as MainActivity,
                    REQUEST_TURN_DEVICE_LOCATION_ON
                )
            } catch (sendEx: IntentSender.SendIntentException) {
                Log.d(GEO_TAG, "Error geting location settings resolution: " + sendEx.message)
            }
        } else {
            Log.d(GEO_TAG, "checkDeviceLocationSettingsAndStartGeofence: FAILED $exception")
        }
    }
    locationSettingsResponseTask.addOnCompleteListener {
        if (it.isSuccessful) {
            if (geofenceList.isEmpty()) {
                populateGeoFanceList()
            }
            Log.d(GEO_TAG, "checkDeviceLocationSettingsAndStartGeofence: SUCCESS")
        }
    }
}