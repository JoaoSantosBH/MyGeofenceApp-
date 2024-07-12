package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class Geometry(
    @SerialName("bounds") val bounds: Bounds? = Bounds(),
    @SerialName("location") val location: UserLocation? = UserLocation(),
    @SerialName("location_type") val locationType: String? = EMPTY_STRING,
    @SerialName("viewport") val viewport: Viewport? = Viewport()
)