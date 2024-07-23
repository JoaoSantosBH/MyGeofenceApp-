package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("airportIataCode") val airportIataCode: String? = null,
    @SerializedName("terminal") val terminal: String? = null,
    @SerializedName("gate") val gate: String? = null
) {
    companion object {
        val FAKE_ORIGIN = Origin(
            airportIataCode = "CNF",
            terminal = "4",
            gate = "15A"
        )
    }
}