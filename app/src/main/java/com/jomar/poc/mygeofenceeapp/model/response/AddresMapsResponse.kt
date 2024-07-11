package com.jomar.poc.mygeofenceeapp.model.response


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class AddresMapsResponse(
    @SerializedName("results") val results: List<Result>? = listOf(),
    @SerializedName("status") val status: String? = EMPTY_STRING
)