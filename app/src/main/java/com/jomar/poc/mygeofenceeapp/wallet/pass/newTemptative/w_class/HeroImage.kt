package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.ContentDescription.Companion.FAKE_CONTENT_DESCRIPTION

data class HeroImage(
    @SerializedName("sourceUri") val sourceUri: SourceUri? = SourceUri(),
    @SerializedName("contentDescription") val contentDescription: ContentDescription? = ContentDescription()
) {
    companion object {
        val FAKE_HERO_IMAGE = HeroImage(
            sourceUri = SourceUri.FAKE_SOURCE_URI,
            contentDescription = FAKE_CONTENT_DESCRIPTION
        )
    }
}