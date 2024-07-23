package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName

data class Destination(
    @SerializedName("airportIataCode") val airportIataCode: String? = null,
    @SerializedName("terminal") val terminal: String? = null,
    @SerializedName("gate") val gate: String? = null
) {
    companion object {
        val FAKE_DESTINATION = Destination(
            airportIataCode = "GRU",
            terminal = "1",
            gate = "13"
        )
    }
}