package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.DefaultValue.Companion.FAKE_DEFAULT_VALUE

data class LocalizedIssuerName(
    @SerializedName("defaultValue") val defaultValue: DefaultValue? = null
) {
    companion object {
        val FAKE_LOCALIZED_ISSUER_NAME = LocalizedIssuerName(defaultValue = FAKE_DEFAULT_VALUE)
    }
}