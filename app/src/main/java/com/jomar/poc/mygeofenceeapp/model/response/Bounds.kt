package com.jomar.poc.mygeofenceeapp.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bounds(
    @SerialName("northeast") val northeast: Northeast? = null,
    @SerialName("southwest") val southwest: Southwest? = null
)