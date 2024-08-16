package com.jomar.poc.mygeofenceeapp.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Southwest(
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lng") val lng: Double? = null
)