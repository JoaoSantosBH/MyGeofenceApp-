package com.jomar.poc.mygeofenceeapp

import org.json.JSONArray
import org.json.JSONObject
const val ID = "id"
const val LAT = "latitude"
const val LONG = "longitude"
const val RADIUS = "radius"

fun getJsonMockList() : String {
    val home =  JSONObject()
    home.put(ID,"HOME")
    home.put(LAT,-19.9301908)
    home.put(LONG,-43.9666872)
    home.put(RADIUS,100.0)

    val carol =  JSONObject()
    carol.put(ID,"CAROL APTO")
    carol.put(LAT,-19.923648)
    carol.put(LONG,-43.945055)
    carol.put(RADIUS,100.0)

    val school =  JSONObject()
    school.put(ID,"ESCOLA")
    school.put(LAT,-19.940291)
    school.put(LONG,-43.961461)
    school.put(RADIUS,100.0)

    val ivan =  JSONObject()
    ivan.put(ID,"IVAN DENTISTA")
    ivan.put(LAT,-19.9298106)
    ivan.put(LONG,-43.9340675)
    ivan.put(RADIUS,100.0)

    val verdeMar =  JSONObject()
    verdeMar.put(ID,"VERDEMAR")
    verdeMar.put(LAT,-19.926942)
    verdeMar.put(LONG,-43.964864)
    verdeMar.put(RADIUS,100.0)

    val resultList = listOf(home,carol,school,ivan,verdeMar)

    val json = JSONArray()
    for (item in resultList) {
        json.put(item)
    }

    return json.toString()
}

