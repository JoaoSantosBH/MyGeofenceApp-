package com.jomar.poc.mygeofenceeapp.model

import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING

class GeofenceJsonResponse : ArrayList<GeofenceJsonItemResponse>()
data class GeofenceJsonItemResponse(
    @SerializedName("id") val id: String? = EMPTY_STRING,
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null,
    @SerializedName("radius") val radius: Float? = null,
)

data class GeofenceModel(
    val id: String = EMPTY_STRING,
    val latitude: Double,
    val longitude: Double,
    val radius: Float = 100f
) {

    companion object {

        fun GeofenceJsonResponse.toDomain() = this.map { it.toDomain() }

        fun GeofenceJsonItemResponse.toDomain() = GeofenceModel(
            id = this.id ?: EMPTY_STRING,
            latitude = this.latitude ?: 0.0,
            longitude = this.longitude ?: 0.0,
            radius = this.radius ?: 100f
        )

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
            latitude = -19.9298106,
            longitude = -43.9340675
        )

        val ESCOLA = GeofenceModel(
            id = "ESCOLA",
            latitude = -19.940291,
            longitude = -43.961461
        )

        val ALL_GEOFENCES = listOf(MINHA_CASA, CASA_CAROL, ESCOLA, DR_IVAN_DENTISTA, VERDE_MAR)

    }

}