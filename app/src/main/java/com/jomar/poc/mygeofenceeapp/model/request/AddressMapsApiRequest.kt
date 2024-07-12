package com.jomar.poc.mygeofenceeapp.model.request


data class AddressMapsApiRequest(
    val address: String,
    val key: String,
    val sensor: Boolean = false,
    val addressParam: String = "address",
    val keyParam: String = "key",
    val sensorParam: String = "sensor"

) {
    companion object {
        val MODEL_API = AddressMapsApiRequest(
            address = "Rua Aristóteles Caldeira 368",
            key = "U_SECRET_KEY",
            sensor = false
        )
    }
}
