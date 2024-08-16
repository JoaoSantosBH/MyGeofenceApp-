package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressResult(
    @SerialName("address_components") val addressComponents: List<AddressComponent>? = null,
    @SerialName("formatted_address") val formattedAddress: String?= EMPTY_STRING,
    @SerialName("geometry") val geometry: Geometry? = Geometry(),
    @SerialName("place_id") val placeId: String? = EMPTY_STRING,
    @SerializedName("plus_code") val plusCode: PlusCode? = PlusCode(),
    @SerialName("types") val types: List<String>? = listOf()
)