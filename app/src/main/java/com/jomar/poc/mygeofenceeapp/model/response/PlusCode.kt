package com.jomar.poc.mygeofenceeapp.model.response

import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class PlusCode(
    @SerializedName("compound_code") val compoundCode: String? = EMPTY_STRING,
    @SerializedName("global_code") val globalCode: String? = EMPTY_STRING
)