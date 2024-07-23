package com.jomar.poc.mygeofenceeapp.wallet.pass.jwt


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.FlightClasse

data class PayloadJWT(
    @SerializedName("flightObjects") val flightObjects: List<String?>? = null,
    @SerializedName("flightClasses") val flightClasses: List<FlightClasse>? = listOf()

)