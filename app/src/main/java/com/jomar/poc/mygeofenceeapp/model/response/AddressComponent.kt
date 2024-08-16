package com.jomar.poc.mygeofenceeapp.model.response


import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressComponent(
    @SerialName("long_name") val longName: String? = EMPTY_STRING,
    @SerialName("short_name") val shortName: String? = EMPTY_STRING,
    @SerialName("types") val types: List<String?>? = null
)