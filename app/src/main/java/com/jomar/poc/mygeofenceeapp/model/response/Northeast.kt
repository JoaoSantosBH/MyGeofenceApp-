package com.jomar.poc.mygeofenceeapp.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Northeast(
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lng") val lng: Double? = null
)