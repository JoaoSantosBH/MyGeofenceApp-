package com.jomar.poc.mygeofenceeapp.ui.theme

data class GeofenceModel(
    val id: String = "",
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val radius: Float = 100f
) {

    companion object {

        val MINHA_CASA = GeofenceModel(
            id = "HOME",
            latitude = -19.9304847003089,
            longitude = -43.96641273294185,
            altitude = 2.0)
        val CASA_CAROL = GeofenceModel(
            id = "CAROL APTO",
            latitude = -19.923648,
            longitude = -43.945055,
            altitude = 2.0)
        val VERDE_MAR = GeofenceModel(
            id = "VERDEMAR",
            latitude = -19.926942,
            longitude = -43.964864,
            altitude = 2.0)
        val DR_IVAN_DENTISTA = GeofenceModel(
            id = "IVAN DENTISTA",
            latitude = -19.923595713,
            longitude = -43.944977803,
            altitude = 2.0)
        val ESCOLA = GeofenceModel(
            id = "ESCOLA",
            latitude = -19.940291,
            longitude = -43.961461,
            altitude = 2.0)

        val ALL_GEOFENCES = listOf(MINHA_CASA, CASA_CAROL, ESCOLA, DR_IVAN_DENTISTA, VERDE_MAR)

    }

}