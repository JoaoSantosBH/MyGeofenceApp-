package com.jomar.poc.mygeofenceeapp

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel.Companion.ALL_GEOFENCES

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
        Log.d(TAG, "populateGeoFanceList")
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
    Log.d(TAG, "addMyActualCustomLocation")
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
fun registerGeofences(applicationContext: Context, geofencePendingIntent: PendingIntent) {
    Log.d(TAG, "registerGeofences")
    if (ActivityCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        Log.d(TAG, "registerGeofences: PERMISSION_DENIED")
        return
    }
    geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent).run {
        addOnSuccessListener {
            Log.d(TAG, "Geofences added")
            Log.d(TAG, "Geofences size list ${geofenceList.size}")
        }
        addOnFailureListener {
            Log.d(TAG, "Geofences failed")
        }
    }
}


fun stopGeoFences(geofencePendingIntent: PendingIntent) {
    geofencingClient.removeGeofences(geofencePendingIntent).run {
        addOnSuccessListener {
            Log.d(TAG, "Geofences removed")
        }
        addOnFailureListener {
            Log.d(TAG, "Geofences failed")
        }
    }
}

fun getGeofencingRequest(): GeofencingRequest {
    return GeofencingRequest.Builder().apply {
        setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        addGeofences(geofenceList)
    }.build()
}