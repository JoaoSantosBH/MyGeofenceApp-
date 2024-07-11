package com.jomar.poc.mygeofenceeapp.model

import com.jomar.poc.mygeofenceeapp.EMPTY_STRING

data class GeofenceModel(
    val id: String = EMPTY_STRING,
    val latitude: Double,
    val longitude: Double,
    val radius: Float = 100f
) {

    companion object {

        val MINHA_CASA = GeofenceModel(
            id = "HOME",
            latitude = -19.9301908,
            longitude = -43.9666872
        )

        val CASA_CAROL = GeofenceModel(
            id = "CAROL APTO",
            latitude = -19.923648,
            longitude = -43.945055
        )

        val VERDE_MAR = GeofenceModel(
            id = "VERDEMAR",
            latitude = -19.926942,
            longitude = -43.964864
        )

        val DR_IVAN_DENTISTA = GeofenceModel(
            id = "IVAN DENTISTA",
            latitude = -19.923595713,
            longitude = -43.944977803
        )

        val ESCOLA = GeofenceModel(
            id = "ESCOLA",
            latitude = -19.940291,
            longitude = -43.961461
        )

        val ALL_GEOFENCES = listOf(MINHA_CASA, CASA_CAROL, ESCOLA, DR_IVAN_DENTISTA, VERDE_MAR)

    }

}