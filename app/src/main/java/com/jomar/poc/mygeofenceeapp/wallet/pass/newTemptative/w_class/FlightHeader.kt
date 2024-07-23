package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.Carrier.Companion.FAKE_CARRIER

data class FlightHeader(
    @SerializedName("carrier") val carrier: Carrier? = Carrier(),
    @SerializedName("flightNumber") val flightNumber: String? = EMPTY_STRING
) {
    companion object {
        val FAKE_FLIGHT_HEADER = FlightHeader(
            carrier = FAKE_CARRIER,
            flightNumber = "1234"
        )
    }
}