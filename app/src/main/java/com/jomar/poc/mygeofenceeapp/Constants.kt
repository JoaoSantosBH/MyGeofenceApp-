package com.jomar.poc.mygeofenceeapp

import com.google.android.gms.location.Geofence

const val TAG = "TaskGeofence"
//https://www.gps-coordinates.net/
//https://developer.android.com/develop/sensors-and-location/location/geofencing#kotlin
//https://www.kodeco.com/7372-geofencing-api-tutorial-for-android/page/2


const val GEOFENCE_EXPIRATION_IN_MILLISECONDS = Geofence.NEVER_EXPIRE
const val CHANNEL_ID = "com.jomar.poc.mygeofenceeapp"
const val NOTIFICATION_ID = 3333
const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
