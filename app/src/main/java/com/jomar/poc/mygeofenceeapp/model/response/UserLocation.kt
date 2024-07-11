package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable

data class UserLocation(
    @SerializedName("lat") val lat: Double? = null,
    @SerializedName("lng") val lng: Double? = null
) {
    companion object {

         val EMPTY_LOCATION = UserLocation(
            lat = 0.0,
            lng = 0.0
        )
    }
}