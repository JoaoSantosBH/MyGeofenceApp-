package com.jomar.poc.mygeofenceeapp.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.jomar.poc.mygeofenceeapp.GeofenceBroadcastReceiver
import com.jomar.poc.mygeofenceeapp.MainActivity.Companion.ACTION_GEOFENCE_EVENT
import com.jomar.poc.mygeofenceeapp.TAG
import com.jomar.poc.mygeofenceeapp.geofenceList
import com.jomar.poc.mygeofenceeapp.geofencingClient
import com.jomar.poc.mygeofenceeapp.populateGeoFanceList
import com.jomar.poc.mygeofenceeapp.registerGeofences

class BootReceiver : BroadcastReceiver() {

    var receiveCcontext: Context? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        receiveCcontext = context
        Log.d(TAG, "onReceive after device restarted")
        geofencingClient = geofencingClient(context!!)
        if (geofenceList.isEmpty()) {
            populateGeoFanceList()
        }
        registerGeofences(context, geofencePendingIntent)
    }
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(receiveCcontext, GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        val pendingFlags = if (Build.VERSION.SDK_INT >= 23) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        PendingIntent.getBroadcast(receiveCcontext, 0, intent, pendingFlags)
    }

}