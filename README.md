# MyGeofenceApp
Geofence app with android kotlin
### Obtaining a Google Maps API Key
Open _Google Cloud Platform_ and [create a new project]
Select _APIs & Services ▸ Library from the navigation menu_.
Select _Maps SDK for Android_.  
Click _Enable_, or _Manage_ if already enabled.
Click on _Credentials_, _Credentials in the API Manager_, _Create credentials_ and then choose _API key_.
1.  Copy your API key value. In your project, open  `debug/res/values/google_maps_api.xml`  and replace  `YOUR_KEY_HERE`  with the copied value.

### ADD LIB
implementation 'com.google.android.gms:play-services-location:16.0.0'

## Creating a Geofence

To manipulate geofences, you need to use the  _GeofencingClient_, so open  _ReminderRepository.kt_  and add a new property:

You’ll also need to import  `com.google.android.gms.location.LocationServices`.

lateinit var geofencingClient: GeofencingClient

fun geofencingClient(context: Context) = LocationServices.getGeofencingClient(context)

geofencingClient = geofencingClient(this)  
if (geofenceList.isEmpty()) {  
populateGeoFanceList()  
}  
registerGeofences(applicationContext, geofencePendingIntent)

### Building the Geofence

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
Take a look at the  `Geofence.Builder`  code in your new method:

1.  _RequestId_: This id uniquely identifies the geofence within your app. You obtain this from the reminder model.
2.  _latitude_,  _longitude_  and  _radius_: You also get these from the reminder model at the top of the new method.
3.  _TransitionType_: To trigger an event when the user enters the geofence, use  `GEOFENCE_TRANSITION_ENTER`. Other options are  `GEOFENCE_TRANSITION_EXIT`  and  `GEOFENCE_TRANSITION_DWELL`. You can learn more about transition options  [here](https://developers.google.com/android/reference/com/google/android/gms/location/GeofencingRequest.Builder.html#setInitialTrigger(int)).
4.  _ExpirationDuration_: Use  `NEVER_EXPIRE`  so this geofence will exist until the user removes it. The other option is to enter a duration (ms) after which the geofence will expire.


### Building the Geofence Request


### Building the Geofence Pending Intent

Create the file  _GeofenceBroadcastReceiver.kt_  and add the following code:

Manifest

<receiver
android:name=".GeofenceBroadcastReceiver"
android:enabled="true"
android:exported="true" />
<service
android:name=".GeofenceTransitionsJobIntentService"
android:exported="true"
android:permission="android.permission.BIND_JOB_SERVICE" />

## Removing a Geofence



Referências:

https://www.gps-coordinates.net/  
https://developer.android.com/develop/sensors-and-location/location/geofencing#kotlin  
https://www.kodeco.com/7372-geofencing-api-tutorial-for-android/page/2