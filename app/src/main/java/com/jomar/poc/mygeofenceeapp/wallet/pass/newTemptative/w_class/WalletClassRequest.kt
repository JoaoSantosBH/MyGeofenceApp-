package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING

data class WalletClassRequest(
    @SerializedName("aud") val aud: String? = EMPTY_STRING,
    @SerializedName("origins") val origins: List<String>? = listOf(),
    @SerializedName("iss") val iss: String? = EMPTY_STRING,
    @SerializedName("iat") val iat: Int? = 0,
    @SerializedName("typ") val typ: String? = EMPTY_STRING,
    @SerializedName("payload") val payload: Payload? = Payload()
) {

    companion object {

        fun createFakeWalletClassRequest(): WalletClassRequest {
            return WalletClassRequest(
                iss = "ISSUER_ID.FLIGHT_CLASS_ID",
                aud = "https://www.google.com",
                typ = "JWT",
            )
        }
    }
}