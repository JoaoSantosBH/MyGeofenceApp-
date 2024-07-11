package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable

data class Geometry(
    @SerializedName("bounds") val bounds: Bounds? = Bounds(),
    @SerializedName("location") val location: UserLocation? = UserLocation(),
    @SerializedName("location_type") val locationType: String? = EMPTY_STRING,
    @SerializedName("viewport") val viewport: Viewport? = Viewport()
)