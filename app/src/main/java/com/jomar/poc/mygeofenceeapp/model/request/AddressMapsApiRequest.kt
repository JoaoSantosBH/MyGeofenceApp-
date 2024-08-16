package com.jomar.poc.mygeofenceeapp.model.request

import com.jomar.poc.mygeofenceeapp.EMPTY_STRING


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
            address = EMPTY_STRING,
            key = EMPTY_STRING,
            sensor = false
        )
    }
}
