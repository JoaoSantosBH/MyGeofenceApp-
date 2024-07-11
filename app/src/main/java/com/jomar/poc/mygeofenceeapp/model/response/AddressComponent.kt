package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddressComponent(
    @SerializedName("long_name") val longName: String? = null,
    @SerializedName("short_name") val shortName: String? = null,
    @SerializedName("types") val types: List<String?>? = null
)