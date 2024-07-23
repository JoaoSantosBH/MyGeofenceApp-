package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("flightClasses") val flightClasses: List<FlightClasse>? = listOf()
)