package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj


import com.google.gson.annotations.SerializedName

data class ReservationInfo(
    @SerializedName("confirmationCode") val confirmationCode: String? = null,
    @SerializedName("eticketNumber") val eticketNumber: String? = null
) {
    companion object {
        val FAKE_RESERVATION_INFO = ReservationInfo(
            confirmationCode = "12345556333",
            eticketNumber = "9877666"
        )
    }

}