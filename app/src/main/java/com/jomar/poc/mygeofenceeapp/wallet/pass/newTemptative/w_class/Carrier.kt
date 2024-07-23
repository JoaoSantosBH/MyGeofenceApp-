package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.AirlineLogo.Companion.FAKE_AIRLINE_LOGO

data class Carrier(
    @SerializedName("carrierIataCode") val carrierIataCode: String? = EMPTY_STRING,
    @SerializedName("airlineLogo") val airlineLogo: AirlineLogo? = AirlineLogo()
) {
    companion object {
        val FAKE_CARRIER = Carrier(
            carrierIataCode = "GR",
            airlineLogo = FAKE_AIRLINE_LOGO
        )
    }
}