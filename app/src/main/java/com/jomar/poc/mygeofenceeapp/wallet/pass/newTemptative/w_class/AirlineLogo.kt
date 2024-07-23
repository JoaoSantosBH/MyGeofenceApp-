package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.ContentDescription.Companion.FAKE_CONTENT_DESCRIPTION

data class AirlineLogo(
    @SerializedName("sourceUri") val sourceUri: SourceUri? = SourceUri(),
    @SerializedName("contentDescription") val contentDescription: ContentDescription? = ContentDescription()
) {
    companion object {
        val FAKE_AIRLINE_LOGO = AirlineLogo(
            sourceUri = SourceUri.FAKE_SOURCE_URI,
            contentDescription = FAKE_CONTENT_DESCRIPTION
        )
    }

}