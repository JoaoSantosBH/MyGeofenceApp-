package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable

data class Bounds(
    @SerializedName("northeast") val northeast: Northeast? = null,
    @SerializedName("southwest") val southwest: Southwest? = null
)