package com.jomar.poc.mygeofenceeapp

import com.jomar.poc.mygeofenceeapp.model.GeofenceModel.Companion.CASA_CAROL
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel.Companion.DR_IVAN_DENTISTA
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel.Companion.ESCOLA
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel.Companion.MINHA_CASA
import com.jomar.poc.mygeofenceeapp.model.GeofenceModel.Companion.VERDE_MAR
import org.json.JSONArray
import org.json.JSONObject
const val ID = "id"
const val LAT = "latitude"
const val LONG = "longitude"
const val RADIUS = "radius"

fun getJsonMockList() : String {
    val home =  JSONObject()
    val carol =  JSONObject()
    val verdeMar =  JSONObject()
    val ivan =  JSONObject()
    val school =  JSONObject()

    home.apply {
        put(ID,MINHA_CASA)
        put(LAT,MINHA_CASA.latitude)
        put(LONG,MINHA_CASA.longitude)
        put(RADIUS,MINHA_CASA.radius)
    }
    carol.apply {
        put(ID,CASA_CAROL)
        put(LAT,CASA_CAROL.latitude)
        put(LONG,CASA_CAROL.longitude)
        put(RADIUS,CASA_CAROL.radius)
    }
    verdeMar.apply {
        put(ID,VERDE_MAR)
        put(LAT,VERDE_MAR.latitude)
        put(LONG,VERDE_MAR.longitude)
        put(RADIUS,VERDE_MAR.radius)
    }
    ivan.apply {
        put(ID,DR_IVAN_DENTISTA)
        put(LAT,DR_IVAN_DENTISTA.latitude)
        put(LONG,DR_IVAN_DENTISTA.longitude)
        put(RADIUS,DR_IVAN_DENTISTA.radius)
    }
    school.apply {
        put(ID,ESCOLA)
        put(LAT,ESCOLA.latitude)
        put(LONG,ESCOLA.longitude)
        put(RADIUS,ESCOLA.radius)
    }

    val resultList = listOf(home,carol,school,ivan,verdeMar)
    val json = JSONArray()
    for (item in resultList) { json.put(item) }

    return json.toString()
}

