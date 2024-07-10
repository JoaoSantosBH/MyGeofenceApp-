


# MyGeofenceApp
Geofence app with android kotlin


**Obter uma Google Maps API key**:    
Open _Google Cloud Platform_ and [create a new project]
Select _APIs & Services ▸ Library from the navigation menu_.      
Select _Maps SDK for Android_.        
Click _Enable_, or _Manage_ if already enabled.      
Click on _Credentials_, _Credentials in the API Manager_, _
Create credentials_ and then choose _API key_.

Adicionar ao manifest
 ```xml  
 <meta-data    
   android:name="com.google.android.geo.API_KEY"android:value="@string/apikey" />   
```  


**Configuração do Projeto**:
- Adicione as dependências necessárias no arquivo `build.gradle`:
   ```gradle 
   implementation 'com.google.android.gms:play-services-location:18.0.0'     
**Permissões**:    
Adicione as permissões necessárias no arquivo `AndroidManifest.xml`:

```xml      
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> 
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />   
```  

**Habilitar Localização**:
- Verifique se os serviços de localização estão habilitados e peça permissão ao usuário, se necessário.    
  **Criar e Configurar Geofence**:
- Use a classe `Geofence` para definir as propriedades da geofence, como o raio e a duração.
 ```kotlin     Geofence.Builder()      
      .setRequestId(ALL_GEOFENCES[i].id)      
      .setCircularRegion(      
             ALL_GEOFENCES[i].latitude,      
            ALL_GEOFENCES[i].longitude,      
            ALL_GEOFENCES[i].radius      
  )      
      .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)      
  . setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)      
      .build()    
   
 ```
**Adicionar Geofence ao GeofencingClient**:
- Crie uma lista de geofences e adicione-as ao `GeofencingClient`.
 ```kotlin          
 - fun geofencingClient(context: Context) = LocationServices.getGeofencingClient(context)    
 geofencingClient = geofencingClient(this) registerGeofences(applicationContext, geofencePendingIntent) geofencingClient.addGeofences(getGeofencingRequest(), geofencePendingIntent).run {       addOnSuccessListener {      
       Log.d(TAG, "Geofences added")      
       }      
       addOnFailureListener {      
       Log.d(TAG, "Geofences failed")      
       }      
   }    
   
```   
**Configurar GeofencingRequest**:
- Configure a solicitação de geofencing.

```kotlin     
 fun getGeofencingRequest(): GeofencingRequest {  return GeofencingRequest.Builder().apply {      
  setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)      
addGeofences(geofenceList) }.build() }    
   
``` **Criar um PendingIntent**:  
- Crie um `PendingIntent` para lidar com as transições de geofence. ```kotlin       private val geofencePendingIntent: PendingIntent by lazy {      
  val intent = Intent(this, GeofenceBroadcastReceiver::class.java)      
  intent.action = ACTION_GEOFENCE_EVENT      
  val pendingFlags = if (Build.VERSION.SDK_INT >= 23) {      
  PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE      
  } else {      
  PendingIntent.FLAG_UPDATE_CURRENT      
  }      
  PendingIntent.getBroadcast(this, 0, intent, pendingFlags)      
  }  
  
```   
**Implementar BroadcastReceiver**:


- Crie uma classe que estende `BroadcastReceiver` para lidar com os eventos de transição da geofence.
 ```kotlin  class GeofenceBroadcastReceiver : BroadcastReceiver() {      
      override fun onReceive(context: Context, intent: Intent) {    
.... } 
```   
Referências:

https://www.gps-coordinates.net/  
https://developer.android.com/develop/sensors-and-location/location/geofencing#kotlin https://www.kodeco.com/7372-geofencing-api-tutorial-for-android/page/1