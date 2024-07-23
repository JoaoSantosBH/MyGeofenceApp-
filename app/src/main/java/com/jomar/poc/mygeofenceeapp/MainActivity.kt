package com.jomar.poc.mygeofenceeapp

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.pay.Pay
import com.google.android.gms.pay.PayApiAvailabilityStatus
import com.google.android.gms.pay.PayClient
import com.google.wallet.button.ButtonType
import com.google.wallet.button.WalletButton
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel
import com.jomar.poc.mygeofenceeapp.model.request.AddressMapsApiRequest
import com.jomar.poc.mygeofenceeapp.model.response.UserLocation
import com.jomar.poc.mygeofenceeapp.remote.ApiResponse
import com.jomar.poc.mygeofenceeapp.remote.KtorClient
import com.jomar.poc.mygeofenceeapp.ui.theme.Pink40
import com.jomar.poc.mygeofenceeapp.wallet.createWalletClassJson
import com.jomar.poc.mygeofenceeapp.wallet.createWalletObjJson
import com.jomar.poc.mygeofenceeapp.wallet.getJwtObj
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.FlightClasse.Companion.getFakeFlightClasse
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj.WalletObjRequest.Companion.getFakeWalletObjRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

lateinit var geofencingClient: GeofencingClient


@RequiresApi(Build.VERSION_CODES.Q)

class MainActivity : ComponentActivity() {

    //WALLET
    private lateinit var walletClient: PayClient

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        val pendingFlags = if (Build.VERSION.SDK_INT >= 23) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        PendingIntent.getBroadcast(this, 0, intent, pendingFlags)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //WALLET
        // TODO: Instantiate the client
        walletClient = Pay.getClient(this)

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
            fetchCanUseGoogleWalletApi()
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
                        var addedAddress by remember { mutableStateOf(EMPTY_STRING) }
                        var radius by remember { mutableStateOf(EMPTY_STRING) }

                        Text(addedAddress)

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
                                                if (response.data.status != "ZERO_RESULTS") {
                                                    val location =
                                                        response.data.results?.get(0)?.geometry?.location
                                                    addedAddress =
                                                        if (response.data.results?.get(0)?.formattedAddress?.isEmpty() == true) EMPTY_STRING else response.data.results?.get(
                                                            0
                                                        )?.formattedAddress.toString()
                                                    if (location != null) addMyLocation(
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
                                                    response.error.toString(),
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
                        Spacer(modifier = Modifier.padding(20.dp))
//WALLET
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                // Default
                                WalletButton(onClick = {
                                        addToGoogleWallet()

                                })

                                // Customized look
                                WalletButton(onClick = { addToGoogleWallet() }, modifier = Modifier.width(350.dp))

                                // Condensed version
                                WalletButton(onClick = {  addToGoogleWallet()}, type = ButtonType.AddCondensed)
                            }
                        }

                    }


                    Log.d(TAG, "shouldShowPermissionRationale: $shouldShowPermissionRationale")
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
        geofencingClient = geofencingClient(this)
        if (geofenceList.isEmpty()) {
            populateGeoFanceList()
        }
        registerGeofences(applicationContext, geofencePendingIntent)
    }


    fun addMyLocation(location: UserLocation, radius: String = DEFAULT_NAME_LOCATION) {
        Log.d(TAG, "addMyLocation: $location.")
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

        addMyActualCustomLocation(
            GeofenceModel(
                id = DEFAULT_NAME_LOCATION,
                latitude = location.lat ?: 0.0,
                longitude = location.lng ?: 0.0,
                radius = radius.toFloat()
            )
        )
        Toast.makeText(this, "Geofence added 100m radius => $location", Toast.LENGTH_SHORT)
            .show()
        Log.d(TAG, "addMyLocation: $location")
        registerGeofences(applicationContext, geofencePendingIntent)


    }


    override fun onDestroy() {
        super.onDestroy()
        stopGeoFences(geofencePendingIntent)
    }

    override fun onStart() {
        super.onStart()
        checkDeviceLocationSettingsAndStartGeofence()

    }

    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "MainActivity.action.ACTION_GEOFENCE_EVENT"
    }

    //WALLET
    // TODO: Create a method to check for the Google Wallet SDK and API
    private fun fetchCanUseGoogleWalletApi() {
        walletClient
            .getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
            .addOnSuccessListener { status ->
                if (status == PayApiAvailabilityStatus.AVAILABLE) {
            //TODO show button
                    Toast.makeText(this, "Google Wallet SDK is available", Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener {
                // Hide the button and optionally show an error message
                Toast.makeText(this, "Google Wallet SDK is not available", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addToGoogleWallet() {
        walletClient.savePassesJwt(token, this, addToGoogleWalletRequestCode)
        val jsonClass = createWalletClassJson(getFakeFlightClasse())
        val jsonObject = createWalletObjJson(getFakeWalletObjRequest())
        val jwt = getJwtObj(jsonClass,jsonObject)
        walletClient.savePasses(jwt.toString(), this, addToGoogleWalletRequestCode)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addToGoogleWalletRequestCode) {
            when (resultCode) {
                RESULT_OK -> {
                    // Pass saved successfully. Consider informing the user.
                    Toast.makeText(this, "Pass saved successfully", Toast.LENGTH_SHORT).show()
                }

                RESULT_CANCELED -> { // ADICIONAR UM  JWT não assinado com o objet
                    // Save canceled
                    Toast.makeText(this, "Save canceled", Toast.LENGTH_SHORT).show()
                }

                PayClient.SavePassesResult.SAVE_ERROR ->
                    data?.let { intentData ->
                        val errorMessage = intentData.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE)
                        // Handle error. Consider informing the user.
                        Log.e("SavePassesResult", errorMessage.toString())
                    }

                else -> {
                    // Handle unexpected (non-API) exception
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //WALLET END

    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(this)
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    exception.startResolutionForResult(
                        this@MainActivity,
                        REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error geting location settings resolution: " + sendEx.message)
                }
            } else {
                Log.d(TAG, "checkDeviceLocationSettingsAndStartGeofence: FAILED $exception")
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
                if (geofenceList.isEmpty()) {
                    populateGeoFanceList()
                }
                Log.d(TAG, "checkDeviceLocationSettingsAndStartGeofence: SUCCESS")
            }
        }
    }
}