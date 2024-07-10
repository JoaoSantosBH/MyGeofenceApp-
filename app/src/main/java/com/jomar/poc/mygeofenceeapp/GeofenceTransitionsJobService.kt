package com.jomar.poc.mygeofenceeapp

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsJobService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork")
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent?.hasError()!!) {
            val errorMessage = geofencingEvent.errorCode
            Log.d(TAG, "onHandleWork hasError: $errorMessage")
            return
        }

        handleEvent(geofencingEvent)
    }

    private fun handleEvent(event: GeofencingEvent) {
        Log.d(TAG, "handleEvent in Job")
        if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
        Log.d(TAG, "handleEvent:  Geofence entered")
        }
    }

}
