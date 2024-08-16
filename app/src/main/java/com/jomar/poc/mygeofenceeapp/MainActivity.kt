package com.jomar.poc.mygeofenceeapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jomar.poc.mygeofenceeapp.model.GeofenceJsonItemResponse
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel
import com.jomar.poc.mygeofenceeapp.model.request.AddressMapsApiRequest
import com.jomar.poc.mygeofenceeapp.model.response.UserLocation
import com.jomar.poc.mygeofenceeapp.remote.ApiResponse
import com.jomar.poc.mygeofenceeapp.remote.KtorClient
import com.jomar.poc.mygeofenceeapp.ui.SharedPreferencesUtil.addNewGeofenceToSharedPrefs
import com.jomar.poc.mygeofenceeapp.ui.SharedPreferencesUtil.getJsonFromSharedPreferences
import com.jomar.poc.mygeofenceeapp.ui.SharedPreferencesUtil.saveJsonToSharedPreferences
import com.jomar.poc.mygeofenceeapp.ui.theme.Pink40
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@RequiresApi(Build.VERSION_CODES.Q)

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

//    private val geofencePendingIntent: PendingIntent by lazy {
//        val intent = Intent(applicationContext, GeofenceBroadcastReceiver::class.java)
//        intent.action = ACTION_GEOFENCE_EVENT
//        val pendingFlags = if (Build.VERSION.SDK_INT >= 23) {
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
//        } else {
//            PendingIntent.FLAG_UPDATE_CURRENT
//        }
//        PendingIntent.getBroadcast(this, 0, intent, pendingFlags)
//    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Criar lista mock inicial do app
        val mock = getJsonMockList()
        //Salvar lista no shared
        val muSharedList = getJsonFromSharedPreferences(applicationContext, GEO_LIST_KEY)
        if (muSharedList == null) saveJsonToSharedPreferences(applicationContext,GEO_LIST_KEY, mock)
////



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        setContent {
            val hasAllPermissions = hasAllPermissions(this, geoFencePermissions)
            var locationPermissionsGranted by remember {
                mutableStateOf(hasAllPermissions)
            }

            var shouldShowPermissionRationale by remember {
                mutableStateOf(
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                )
            }

            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }

            var currentPermissionsStatus by remember {
                mutableStateOf(
                    decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
                )
            }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    locationPermissionsGranted =
                        permissions.values.reduce { acc, isPermissionGranted ->
                            acc && isPermissionGranted
                        }

                    if (!locationPermissionsGranted) {
                        shouldShowPermissionRationale =
                            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                    shouldDirectUserToApplicationSettings =
                        !shouldShowPermissionRationale && !locationPermissionsGranted
                    currentPermissionsStatus = decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
                })

            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START &&
                        !locationPermissionsGranted &&
                        !shouldShowPermissionRationale
                    ) {
                        requestPermissionLauncher.launch(geoFencePermissions)
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
            )
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }) { contentPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Pink40),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gol_icon),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        val isEnabled = remember { mutableStateOf(true) }
                        var address by remember { mutableStateOf(EMPTY_STRING) }
                        var name by remember { mutableStateOf(EMPTY_STRING) }
                        var addedAddress by remember { mutableStateOf(EMPTY_STRING) }
                        var radius by remember { mutableStateOf(EMPTY_STRING) }

                        Text(addedAddress)
                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = name ,
                            onValueChange = { name = it },
                            label = { Text("Nome da Localização") }

                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Endereço") }

                        )
                        TextField(
                            value = radius,
                            onValueChange = { if (it.isDigitsOnly()) radius = it },
                            label = { Text("Raio da geofance em metros") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {

                                if (radius.isEmpty()) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Por favor informe os dados",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    runBlocking {
                                        val response = KtorClient().getAddressLocation(
                                            AddressMapsApiRequest.MODEL_API.copy(address = address)
                                        )

                                        when (response) {
                                            is ApiResponse.Success -> {
                                                if (response.data.status == "REQUEST_DENIED") {
                                                    Toast.makeText(
                                                        this@MainActivity,
                                                        "REQUEST_DENIED",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    isEnabled.value = true
                                                } else if (response.data.status != "ZERO_RESULTS" && response.data.status != "REQUEST_DENIED") {
                                                    val location =
                                                        response.data.results?.get(0)?.geometry?.location
                                                    addedAddress =
                                                        if (response.data.results?.get(0)?.formattedAddress?.isEmpty() == true) EMPTY_STRING else response.data.results?.get(
                                                            0
                                                        )?.formattedAddress.toString()
                                                    if (location != null) addMyLocation(
                                                        name,
                                                        location,
                                                        radius
                                                    )
                                                    isEnabled.value = false
                                                } else {
                                                    Toast.makeText(
                                                        this@MainActivity,
                                                        "Endereço não encontrado",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    isEnabled.value = true
                                                }

                                            }

                                            is ApiResponse.Failure -> {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    response.exception.toString().drop(49).substring(0,32),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                isEnabled.value = true
                                            }
                                        }
                                    }
                                }


                            }, enabled = isEnabled.value
                        ) {
                            Text(text = "+ Geofence")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier
                                .padding(contentPadding)
                                .fillMaxWidth(),
                            text = "Location Permissions",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            modifier = Modifier
                                .padding(contentPadding)
                                .fillMaxWidth(),
                            text = "Current Permission Status: $currentPermissionsStatus",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }


//                    Log.d(GEO_TAG, "shouldShowPermissionRationale: $shouldShowPermissionRationale")
                    if (shouldShowPermissionRationale) {

                        LaunchedEffect(true) {
                            scope.launch {
                                val userAction = snackbarHostState.showSnackbar(
                                    message = "Please authorize location permissions",
                                    actionLabel = "Approve",
                                    duration = SnackbarDuration.Indefinite,
                                    withDismissAction = true
                                )
                                when (userAction) {
                                    SnackbarResult.ActionPerformed -> {
                                        shouldShowPermissionRationale = false
                                        requestPermissionLauncher.launch(geoFencePermissions)
                                    }

                                    SnackbarResult.Dismissed -> {
                                        shouldShowPermissionRationale = false
                                    }
                                }
                            }
                        }
                    }
                    if (shouldDirectUserToApplicationSettings) {
                        openApplicationSettings(this, packageName)
                    }
                }
            }

        }
        startGeofence()
    }



    private fun startGeofence() {
        geofencingClient = geofencingClient(applicationContext)
        Log.d(GEO_TAG, "geofencingClient MainActivity: $geofencingClient")

         val mapper = getMappedGeoFences(applicationContext)

        if (geofenceList.isEmpty()) {
            if (mapper != null) {
                populateGeoFanceList(mapper)
            }
        }
        registerGeofences(applicationContext)
    }


    fun addMyLocation(name: String, location: UserLocation, radius: String = DEFAULT_NAME_LOCATION) {
        Log.d(GEO_TAG, "addMyLocation: $location.")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val newLocation = GeofenceModel(
            id = name ?: DEFAULT_NAME_LOCATION,
            latitude = location.lat ?: 0.0,
            longitude = location.lng ?: 0.0,
            radius = radius.toFloat()
        )
        addMyActualCustomLocation(newLocation)
        val geofenceModel = GeofenceJsonItemResponse(
            id = newLocation.id,
            latitude = newLocation.latitude,
            longitude = newLocation.longitude,
            radius = newLocation.radius
        )
        addNewGeofenceToSharedPrefs(applicationContext, geofenceModel)
        Toast.makeText(this, "Geofence added 100m radius => $location", Toast.LENGTH_SHORT)
            .show()
        Log.d(GEO_TAG, "addMyLocation: $location")
        registerGeofences(applicationContext)

    }


    override fun onDestroy() {
        super.onDestroy()
        stopGeoFences()
    }

    override fun onStart() {
        super.onStart()
        checkDeviceLocationSettingsAndStartGeofence(applicationContext)

    }

    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "MainActivity.action.ACTION_GEOFENCE_EVENT"
    }


}