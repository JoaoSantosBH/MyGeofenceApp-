package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName

data class SourceUri(
    @SerializedName("uri") val uri: String? = null
) {
    companion object {
        val FAKE_SOURCE_URI = SourceUri(
            uri = "https://www.voegol.com.br/themes/custom/voegol/images/logos/logo-gol.svg"
        )
    }
}