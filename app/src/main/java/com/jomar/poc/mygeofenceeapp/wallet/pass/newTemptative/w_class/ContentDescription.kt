package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName

data class ContentDescription(
    @SerializedName("defaultValue") val defaultValue: DefaultValue? = DefaultValue()
) {
    companion object {
        val FAKE_CONTENT_DESCRIPTION = ContentDescription(defaultValue = DefaultValue.FAKE_DEFAULT_VALUE)
    }

}