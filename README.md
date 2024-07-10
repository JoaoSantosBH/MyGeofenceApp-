# MyGeofenceApp
Geofence app with android kotlin
### Obtaining a Google Maps API Key
Open _Google Cloud Platform_ and [create a new project]
Select _APIs & Services ▸ Library from the navigation menu_.
Select _Maps SDK for Android_.  
Click _Enable_, or _Manage_ if already enabled.
Click on _Credentials_, _Credentials in the API Manager_, _Create credentials_ and then choose _API key_.
1.  Copy your API key value. In your project, open  `debug/res/values/google_maps_api.xml`  and replace  `YOUR_KEY_HERE`  with the copied value.


Para criar geofences no Android, você pode seguir este resumo simplificado baseado no tutorial da Kodeco:

1. **Configuração do Projeto**:
    - Adicione as dependências necessárias no arquivo `build.gradle`:
      ```gradle
      implementation 'com.google.android.gms:play-services-location:18.0.0'
      ```

2. **Permissões**:
    - Adicione as permissões necessárias no arquivo `AndroidManifest.xml`:
      ```xml
      <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
      <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
      ```

3. **Habilitar Localização**:
    - Verifique se os serviços de localização estão habilitados e peça permissão ao usuário, se necessário.

4. **Criar e Configurar Geofence**:
    - Use a classe `Geofence` para definir as propriedades da geofence, como o raio e a duração.
      ```java
      Geofence geofence = new Geofence.Builder()
          .setRequestId("myGeofenceId")
          .setCircularRegion(latitude, longitude, radius)
          .setExpirationDuration(Geofence.NEVER_EXPIRE)
          .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
          .build();
      ```

5. **Adicionar Geofence ao GeofencingClient**:
    - Crie uma lista de geofences e adicione-as ao `GeofencingClient`.
      ```java
      GeofencingClient geofencingClient = LocationServices.getGeofencingClient(this);
      geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
          .addOnSuccessListener(this, new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                  // Geofence adicionada com sucesso
              }
          })
          .addOnFailureListener(this, new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  // Falha ao adicionar geofence
              }
          });
      ```

6. **Configurar GeofencingRequest**:
    - Configure a solicitação de geofencing.
      ```java
      private GeofencingRequest getGeofencingRequest() {
          GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
          builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
          builder.addGeofence(geofence);
          return builder.build();
      }
      ```

7. **Criar um PendingIntent**:
    - Crie um `PendingIntent` para lidar com as transições de geofence.
      ```java
      private PendingIntent getGeofencePendingIntent() {
          Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
          return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      }
      ```

8. **Implementar BroadcastReceiver**:
    - Crie uma classe que estende `BroadcastReceiver` para lidar com os eventos de transição da geofence.
      ```java
      public class GeofenceBroadcastReceiver extends BroadcastReceiver {
          @Override
          public void onReceive(Context context, Intent intent) {
              GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
              if (geofencingEvent.hasError()) {
                  // Lidar com erro
                  return;
              }
 
              int geofenceTransition = geofencingEvent.getGeofenceTransition();
              if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                  geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                  // Lidar com transição de geofence
              }
          }
      }
      ```


Referências:

https://www.gps-coordinates.net/  
https://developer.android.com/develop/sensors-and-location/location/geofencing#kotlin  
https://www.kodeco.com/7372-geofencing-api-tutorial-for-android/page/2