package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj


import com.google.gson.annotations.SerializedName

data class BoardingAndSeatingInfo(
    @SerializedName("boardingGroup") val boardingGroup: String? = null,
    @SerializedName("seatNumber") val seatNumber: String? = null,
    @SerializedName("seatClass") val seatClass: String? = null
) {
    companion object {
        val FAKE_BOARDING_AND_SEATING_INFO = BoardingAndSeatingInfo(
            boardingGroup = "B",
            seatNumber = "45 anos",
            seatClass = "Economia"
        )
    }

}