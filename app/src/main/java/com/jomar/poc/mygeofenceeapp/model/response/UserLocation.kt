package com.jomar.poc.mygeofenceeapp.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLocation(
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lng") val lng: Double? = null
) {
    companion object {

         val EMPTY_LOCATION = UserLocation(
            lat = 0.0,
            lng = 0.0
        )
    }
}