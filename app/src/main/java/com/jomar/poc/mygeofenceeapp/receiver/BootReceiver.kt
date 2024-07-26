package com.jomar.poc.mygeofenceeapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jomar.poc.mygeofenceeapp.GEO_TAG
import com.jomar.poc.mygeofenceeapp.checkDeviceLocationSettingsAndStartGeofence
import com.jomar.poc.mygeofenceeapp.geofenceList
import com.jomar.poc.mygeofenceeapp.geofencingClient
import com.jomar.poc.mygeofenceeapp.populateGeoFanceList
import com.jomar.poc.mygeofenceeapp.registerGeofences

class BootReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            checkDeviceLocationSettingsAndStartGeofence(context.applicationContext)
        }
        Log.d(GEO_TAG, "onReceive after device restarted")
        geofencingClient = geofencingClient(context?.applicationContext!!)
        Log.d(GEO_TAG, "geofencingClient applicationContext: ${context.applicationContext}")
        Log.d(GEO_TAG, "geofencingClient: $geofencingClient")
        Log.d(GEO_TAG, "geofenceList: $geofenceList")
        startGeofence(context)
    }

    private fun startGeofence(context: Context) {
        if (geofenceList.isEmpty()) {
            populateGeoFanceList()
        }
        registerGeofences(context.applicationContext)
    }


}