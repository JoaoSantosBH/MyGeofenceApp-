package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable

data class Viewport(
    @SerializedName("northeast") val northeast: Northeast? = Northeast(),
    @SerializedName("southwest") val southwest: Southwest? = Southwest()
)