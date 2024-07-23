package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.CLASS_ID
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj.Barcode.Companion.FAKE_BARCODE
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj.BoardingAndSeatingInfo.Companion.FAKE_BOARDING_AND_SEATING_INFO
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj.ReservationInfo.Companion.FAKE_RESERVATION_INFO

data class WalletObjRequest(
    @SerializedName("id") val id: String? = null,
    @SerializedName("classId") val classId: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("passengerName") val passengerName: String? = null,
    @SerializedName("reservationInfo") val reservationInfo: ReservationInfo? = null,
    @SerializedName("boardingAndSeatingInfo") val boardingAndSeatingInfo: BoardingAndSeatingInfo? = null,
    @SerializedName("barcode") val barcode: Barcode? = null
) {
    companion object {

        val FAKE_WALLET_OBJ_REQUEST = WalletObjRequest(
            id = CLASS_ID,
            classId = "3388000000022361681.1234567890", //TODO pegar id da classe aqui
            state = "ACTIVE",
            passengerName = "Sr. João Pereira",
            reservationInfo = FAKE_RESERVATION_INFO,
            boardingAndSeatingInfo = FAKE_BOARDING_AND_SEATING_INFO,
            barcode = FAKE_BARCODE
        )

        fun getFakeWalletObjRequest() = FAKE_WALLET_OBJ_REQUEST
    }
}