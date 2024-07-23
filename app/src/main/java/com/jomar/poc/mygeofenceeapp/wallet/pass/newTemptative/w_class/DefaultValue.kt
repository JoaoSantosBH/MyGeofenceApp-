package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName

data class DefaultValue(
    @SerializedName("language") val language: String? = null,
    @SerializedName("value") val value: String? = null
) {
    companion object {
        val FAKE_DEFAULT_VALUE = DefaultValue(
            language = "pt-BR",
            value = "Gol Linhas Aereas"
        )
    }
}