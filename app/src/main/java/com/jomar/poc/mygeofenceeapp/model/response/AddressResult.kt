package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class AddressResult(
    @SerializedName("address_components") val addressComponents: List<AddressComponent>? = listOf(),
    @SerializedName("formatted_address") val formattedAddress: String?= EMPTY_STRING,
    @SerializedName("geometry") val geometry: Geometry? = Geometry(),
    @SerializedName("place_id") val placeId: String? = EMPTY_STRING,
    @SerializedName("types") val types: List<String>? = listOf()
)