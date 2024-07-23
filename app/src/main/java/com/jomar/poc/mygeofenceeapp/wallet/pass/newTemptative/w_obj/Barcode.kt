package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj


import com.google.gson.annotations.SerializedName

data class Barcode(
    @SerializedName("type") val type: String? = null,
    @SerializedName("value") val value: String? = null,
    @SerializedName("alternateText") val alternateText: String? = null
) {
    companion object {
        val FAKE_BARCODE = Barcode(
            type = "QR_CODE",
            value = "19866877388838",
            alternateText = "Codigo passagem"
        )
    }

}