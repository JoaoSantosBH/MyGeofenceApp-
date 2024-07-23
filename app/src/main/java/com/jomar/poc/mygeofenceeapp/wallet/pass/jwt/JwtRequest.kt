package com.jomar.poc.mygeofenceeapp.wallet.pass.jwt


import com.google.gson.annotations.SerializedName

data class JwtRequest(
        @SerializedName("iss") val iss: String? = null,
        @SerializedName("aud") val aud: String? = null,
        @SerializedName("typ") val typ: String? = null,
        @SerializedName("iat") val iat: String? = null,
        @SerializedName("origins") val origins: List<Any?>? = null,
        @SerializedName("payload") val payload: PayloadJWT? = null
)
