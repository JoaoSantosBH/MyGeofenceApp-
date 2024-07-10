package com.jomar.poc.mygeofenceeapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.jomar.poc.mygeofenceeapp.MainActivity.Companion.ACTION_GEOFENCE_EVENT

class GeofenceBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show()

        if (intent.action == ACTION_GEOFENCE_EVENT) {
            val geofencingEvent = intent.let { GeofencingEvent.fromIntent(it) }
            if (geofencingEvent != null) {
                if (geofencingEvent.hasError()) {
                    val errorMessage = GeofenceStatusCodes
                        .getStatusCodeString(geofencingEvent.errorCode)
                    Log.d(TAG, "Error $errorMessage")
                    return
                }
            }
            val geofenceTransition = geofencingEvent?.geofenceTransition
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT
            ) {
                val triggeringGeofences = geofencingEvent.triggeringGeofences
                val geofenceTransitionDetails = getGeofenceTransitionDetails(
                    context,
                    geofenceTransition,
                    triggeringGeofences
                )
                sendNotification(context, geofenceTransitionDetails)
                Log.d(TAG, geofenceTransitionDetails)
                Toast.makeText(
                    context,
                    "Geofences onReceive $geofenceTransitionDetails",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(context, "Geofences error", Toast.LENGTH_SHORT).show()
                val x = context.getString(R.string.geofence_transition_invalid_type)
                Log.d(TAG, x)
            }
        }

    }

    private fun getGeofenceTransitionDetails(
        context: Context,
        geofenceTransition: Int,
        triggeringGeofences: MutableList<Geofence>?
    ): String {

        val geofenceTransitionString = getTransitionString(context, geofenceTransition)
        val triggeringGeofencesIdsList = ArrayList<String>()
        if (triggeringGeofences != null) {
            for (geofence in triggeringGeofences) {
                triggeringGeofencesIdsList.add(geofence.requestId)
            }
        }
        val triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList)
        return "$geofenceTransitionString: $triggeringGeofencesIdsString"
    }

    private fun getTransitionString(context: Context, transitionType: Int): String {
        return when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> getResourceString(context,R.string.geofence_transition_entered)
            Geofence.GEOFENCE_TRANSITION_EXIT -> getResourceString(context,R.string.geofence_transition_exited)
            else -> getResourceString(context,R.string.unknown_geofence_transition)
        }
    }

    private fun getResourceString(context: Context, id:Int) = context.getString(id)

}