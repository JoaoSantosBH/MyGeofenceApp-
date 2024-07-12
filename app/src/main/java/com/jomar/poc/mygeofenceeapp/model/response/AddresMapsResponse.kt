package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddresMapsResponse(
    @SerialName("results") val results: List<AddressResult>? = listOf(),
    @SerialName("status") val status: String? = EMPTY_STRING
)